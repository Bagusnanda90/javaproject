 
<% 
/* 
 * Page Name  		:  empappraisal_edit.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.appraisal.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.appraisal.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.session.employee.assessment.*" %>
<%@ page import = "com.dimata.harisma.session.employee.appraisal.*" %>
<%@ page import = "com.dimata.harisma.session.employee.assessment.SessAssessmentFormSection" %>
<%@ page import = "com.dimata.harisma.entity.employee.assessment.AssessmentFormMain" %>
<%@ page import = "com.dimata.harisma.entity.employee.assessment.PstAssessmentFormItem" %>
<%@ page import = "com.dimata.harisma.entity.employee.assessment.AssessmentFormSection" %>
<%@ page import = "com.dimata.harisma.entity.employee.assessment.AssessmentFormItem" %>
<%@ page import = "com.dimata.harisma.session.employee.SessEmployee" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_APPRAISAL, AppObjInfo.OBJ_PERFORMANCE_APPRAISAL); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%

	CtrlAppraisal ctrlAppraisal = new CtrlAppraisal(request);
	long oidAppraisalMain = FRMQueryString.requestLong(request, "employee_appraisal_oid");
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");
	int currPage = FRMQueryString.requestInt(request,"currPage");
	int prevPage = FRMQueryString.requestInt(request,"prevPage");
	boolean needSaving = FRMQueryString.requestBoolean(request,"needSaving");
	boolean needSavingApproval = FRMQueryString.requestBoolean(request,"needSavingApproval");
        //for approval
        boolean depHeadAuthorize = FRMQueryString.requestBoolean(request, "hidden_dep_head_authorize");
       // long approvalId = FRMQueryString.requestLong(request, "manager_id");
        
        
        String msg = "";
	String errMsg = "";
        int iErrCode = FRMMessage.ERR_NONE;
	String whereClause = "";
	String orderClause = "";
        
        /*count list All Page*/
        AssessmentFormMain assMain = SessAssessmentMain.getAssessment(oidAppraisalMain);
        int vectSize = SessAssessmentFormItem.getMaxPage(assMain.getOID());
        
	if(!(needSaving)){needSaving=false;}
        
         
        AppraisalMain appraisalMain = new AppraisalMain();
        try{
            appraisalMain = PstAppraisalMain.fetchExc(oidAppraisalMain);
        }catch(Exception ex){
        }
        
        if(iCommand==Command.APPROVE && needSavingApproval){
            
            //long divHeadOid = FRMQueryString.requestLong(request,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DIVISION_HEAD_ID]);
            Date empSignDate = FRMQueryString.requestDate(request,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_EMP_SIGN_DATE]);
            Date assSignDate = FRMQueryString.requestDate(request,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_ASS_SIGN_DATE]);
            Date divSignDate = FRMQueryString.requestDate(request,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DIV_HEAD_SIGN_DATE]);
            //appraisalMain.setDivisionHeadId(divHeadOid);
            appraisalMain.setEmployeeSignDate(empSignDate);
            appraisalMain.setAssessorSignDate(assSignDate);
            appraisalMain.setDivisionHeadSignDate(divSignDate);
            try{
                long oidTemp = PstAppraisalMain.updateExc(appraisalMain);
                if(oidTemp==appraisalMain.getOID()){
                    msg = "<font color='white'>Save data success</font>";
                    needSavingApproval = false;
                }else{
                    msg = "<font color='red'>Save data failed</font>";
                }
            }catch(Exception ex){
            
            }
        }

     //   System.out.println("----------------------------------->"+needSaving);
        if(iCommand==Command.SAVE && needSaving){
            //System.out.println("---------------------<><><<<<");
            ///////////////////////////////////////////////////////////////////
            Vector vSection = new Vector(1,1);
            vSection=SessAssessmentFormSection.getSections(prevPage, assMain.getOID());

            Hashtable hAppraisal = new Hashtable();
            hAppraisal = SessAppraisal.listAppraisal(oidAppraisalMain);
            
            if(vSection.size()>0){
                for(int k=0;k<vSection.size();k++){
                    AssessmentFormSection assSection = new AssessmentFormSection();
                    assSection = (AssessmentFormSection)vSection.get(k);
                    Vector vItems = new Vector(1,1);
                    vItems = SessAssessmentFormItem.listItem(assSection.getOID(), prevPage);
                    int counterApp = 0;
                    for(int l=0;l<vItems.size();l++){
                        AssessmentFormItem assItem = new AssessmentFormItem();
                        assItem = (AssessmentFormItem)vItems.get(l);
                        if(assItem.getPage()==prevPage){
                            if(
                            assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT||
                            assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_SELECT_2_WITHOUT_RANGE||
                            assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_SELECT_1_WITH_RANGE||
                            assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_INPUT_WITH_DOT||
                            assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_INPUT_EMP_COMM||
                            assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_INPUT_ASS_COMM||
                            assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT||
                            assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK||
                            assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_2_COL_ASS_COMM||
                            assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_2_COL_OVERALL_COMM
                        ){
                                long tempHrAppMainOid = oidAppraisalMain;
                                long tempAppraisalOid = FRMQueryString.requestLong(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APPRAISAL_ID]+assItem.getOID());
                                long tempAssItemOid = FRMQueryString.requestLong(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_FORM_ITEM_ID]+assItem.getOID());
                                String tempAssessorComm = FRMQueryString.requestString(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT]+assItem.getOID());
                                String tempEmployeeComm = FRMQueryString.requestString(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT]+assItem.getOID());
                                String tempPoint1 = FRMQueryString.requestString(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_1]+assItem.getOID());
                                String tempPoint2 = FRMQueryString.requestString(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_2]+assItem.getOID());
                                String tempPoint3 = FRMQueryString.requestString(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_3]+assItem.getOID());
                                String tempPoint4 = FRMQueryString.requestString(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_4]+assItem.getOID());
                                String tempPoint5 = FRMQueryString.requestString(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+assItem.getOID());
                                String tempPoint6 = FRMQueryString.requestString(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_6]+assItem.getOID());
                                double tempRating = FRMQueryString.requestDouble(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING]+assItem.getOID());
                                Appraisal appTemp = new Appraisal();
                                try{
                                    appTemp = PstAppraisal.fetchExc(tempAppraisalOid);
                                }catch(Exception ex){}
                                appTemp.setAppMainId(tempHrAppMainOid);
                                appTemp.setAssFormItemId(tempAssItemOid);
                                appTemp.setEmpComment(tempEmployeeComm);
                                appTemp.setAssComment(tempAssessorComm);
                                appTemp.setAnswer_1(tempPoint1);
                                appTemp.setAnswer_2(tempPoint2);
                                appTemp.setAnswer_3(tempPoint3);
                                appTemp.setAnswer_4(tempPoint4);
                                appTemp.setAnswer_5(tempPoint5);
                                appTemp.setAnswer_6(tempPoint6);
                                appTemp.setRating(tempRating);
                                
                                long tempOid = 0;
                                
                                try{
                                    if(appTemp.getOID()>0){
                                        tempOid = PstAppraisal.updateExc(appTemp);
                                    }else{
                                        tempOid = PstAppraisal.insertExc(appTemp);
                                    }
                                    if(tempOid>0){
                                        msg = "<font color='white'>Save data success</font>";
                                    }else{
                                        msg = "<font color='red'>Save data failed</font>";
                                    }
                                    needSaving = false;
                                }catch(Exception ex){}
                                
                            }
                        }
                      }
                  }
            }
            ///////////////////////////////////////////////////////////////////
        }
        
        
	ControlLine ctrLine = new ControlLine();
        
        ///////Control Page
        String strViewForm="";
        int recordToGet = 10;

        
        //System.out.println("MAX PAGE :::: "+vectSize);
        if(iCommand==Command.GOTO){
                start = ctrlAppraisal.actionList(Command.LAST, start, vectSize, recordToGet);
                currPage = SessAssessmentFormItem.getMaxPage(assMain.getOID())+1;
        }
        if((iCommand == Command.FIRST || iCommand == Command.PREV )||
          (iCommand == Command.NEXT || iCommand == Command.LAST)){
            //System.out.println("AWAL START : "+start);
                        start = ctrlAppraisal.actionList(iCommand, start, vectSize, recordToGet);
                        iCommand = Command.VIEW;
                        currPage = start;
            //System.out.println("AKHIR START : "+start);
         }

        if(start<0){
            start = 0;
        }
       
        
        
        
        ///////End Control Page
        
        
        
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Employee Assessment</title>
<script language="JavaScript">

	
//-------------------------------------------
function cmdSearchEmp(){
    val = document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.needSavingApproval.value;
    //("NEED SAVE 1:::: "+val);
    if(val!="true"){
        document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.needSavingApproval.value="true";
    }
    window.open("<%=approot%>/employee/appraisalnew/search.jsp?formName=<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>&empPathId=<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DIVISION_HEAD_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}

function cmdGoToPage(page){
    //alert(page);
    document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.prevPage.value=document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.currPage.value;
    document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.currPage.value=page;
    document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.SAVE)%>";
    document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.prev_command.value="<%=String.valueOf(Command.APPROVE)%>";
    document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="appraisalForm.jsp";
    document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
}

function cmdSetNeedSaving(){
    val = document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.needSavingApproval.value;
    //("NEED SAVE 1:::: "+val);
    if(val!="true"){
        document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.needSavingApproval.value="true";
    }
}

function cmdApprove(){
        document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.needSavingApproval.value="true";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.APPROVE)%>";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="approvalForm.jsp";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
	}
         
function cmdBack(){
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.BACK)%>";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.prev_command.value="<%=String.valueOf(Command.APPROVE)%>";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="appraisalForm.jsp";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
	}

function cmdListFirst(){
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.prev_command.value="<%=String.valueOf(Command.APPROVE)%>";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="appraisalForm.jsp";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
}

function cmdListPrev(){
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.PREV)%>";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.prev_command.value="<%=String.valueOf(Command.APPROVE)%>";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="appraisalForm.jsp";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
	}

function cmdListNext(){
        //alert("NEXT");
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.prev_command.value="<%=String.valueOf(Command.APPROVE)%>";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="appraisalForm.jsp";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
}

function cmdListLast(){
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.LAST)%>";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.prev_command.value="<%=String.valueOf(Command.APPROVE)%>";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="appraisalForm.jsp";
	document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
}

function getThn(){
        <%
         out.println(ControlDatePopup.writeDateCaller(FrmAppraisalMain.FRM_APPRAISAL_MAIN,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_EMP_SIGN_DATE]));
         out.println(ControlDatePopup.writeDateCaller(FrmAppraisalMain.FRM_APPRAISAL_MAIN,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_ASS_SIGN_DATE]));
         out.println(ControlDatePopup.writeDateCaller(FrmAppraisalMain.FRM_APPRAISAL_MAIN,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DIV_HEAD_SIGN_DATE]));
         
         %>
         
    }
    
    function hideObjectForDate(){
        <%
        %>
        val = document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.needSavingApproval.value;
        //alert("NEED SAVE 2 :::: "+val);
        if(val!="true"){
            document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.needSavingApproval.value="true";
        }
    }

    function showObjectForDate(){
        <%
        %>
    }
    
//-------------- For Approval --------------------------
function checkApproval()
{
	var empLoggedIn = "<%=String.valueOf(emplx.getOID())%>";
	var empApprovalSelected = document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EMP_FULLNAME.value;
        //alert("----->"+empApprovalSelected);
        if(empLoggedIn != 0)
	{
		if(empApprovalSelected != 0)
		{
			if(empLoggedIn != empApprovalSelected)
			{
                            document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EMP_FULLNAME[0].selected = "1";    
                            window.open("<%=approot%>/employee/appraisalnew/approvalLogin.jsp?formName=<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>&empPathId=<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DIVISION_HEAD_ID]%>&appraisalMainOid=<%=String.valueOf(oidAppraisalMain)%>&devHeadOid="+empApprovalSelected, null, "height=500,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       	
                        }
		}
		else
		{
			alert('Please choose an authorized manager to approve this Assessment ...');    					
		}
	}
	else
	{
		alert('You should login into Harisma as an authorized user ...'); 
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EMP_FULLNAME.value = "0";   		
	}
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
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<link rel="stylesheet" href="<%=approot%>/styles/form.css" type="text/css">
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #BeginEditable "stylestab" -->  
<style type="text/css">
textarea {width:100%;}
</style>
<!-- #BeginEditable "headerscript" --> 
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
<!-- Untuk Calendar-->
<%=ControlDatePopup.writeTable(approot)%>
<!-- End Calendar-->
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr>  
  <tr> 
    <td bgcolor="#9BC1FF"  ID="MAINMENU" valign="middle" height="15"> <!-- #BeginEditable "menumain" --> 
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
                  <td>
                        <font color="#FF6600" face="Arial"><strong>
                          <!-- #BeginEditable "contenttitle" -->Employee 
  &gt; Employee Assessment <!-- #EndEditable --> 
                        </strong></font>
                  </td>
                </tr>
                <tr> 
                 <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                          <tr> 
                            <td valign="top"> <!-- #BeginEditable "content" --> 
                              <form name="<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>" method="post" action="">
                                <input type="hidden" name="command" value="">
                                <input type="hidden" name="needSaving" value="<%=String.valueOf(needSaving)%>">
                                <input type="hidden" name="needSavingApproval" value="<%=String.valueOf(needSavingApproval)%>">
                                <input type="hidden" name="currPage" value="<%=String.valueOf(currPage)%>">
                                <input type="hidden" name="prevPage" value="<%=String.valueOf(prevPage)%>">
                                <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                <input type="hidden" name="prev_command" value="<%=String.valueOf(iCommand)%>">
                                <input type="hidden" name="employee_appraisal_oid" value="<%=String.valueOf(oidAppraisalMain)%>">
                                <!--for approval-->
                                <input type="hidden" name="hidden_dep_head_authorize" value="<%=String.valueOf(depHeadAuthorize)%>">									  									  
                                <input type="hidden" name="manager_id" value="<%//=String.valueOf(approvalId)%>">				
                                <table width="100%" border="0" cellspacing="1" cellpadding="1" height="504">
                                  <tr> 
                                    <td valign="top" height="551"> 
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <% if(oidAppraisalMain != 0){%>
                                        <tr> 
                                          <td> 
                                            <table  border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td  valign="top" height="20" width="104"> 
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="txtalign">
                                                    <tr> 
                                                      <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                      <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap> 
                                                        
                                                        <div align="center" class="tablink"><a href="empappraisal_edit.jsp?employee_appraisal_oid=<%=String.valueOf(oidAppraisalMain)%>&command=<%=String.valueOf(Command.EDIT)%>" class="tablink"> Employee </a></div>
                                                      </td>
                                                      
                                                      <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                                <td  valign="top" height="20" width="158"> 
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                    <tr> 
                                                      <td   valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/active_left.jpg" width="12" height="29"></td>
                                                      <td   valign="middle" background="<%=approot%>/images/tab/active_bg.jpg" nowrap > 
                                                        <div align="center" class="tablink">Assessment</div>
                                                      </td>
                                                      <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/active_right.jpg" width="12" height="29"></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%}%>
                                        <tr> 
                                          <td> 
                                            <table width="100%" border="0" cellpadding="1" cellspacing="1" style="background-color:<%=bgColorContent%>; ">
                                                <tr><td width="100%">
                                            <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellpadding="1" cellspacing="1" class="tablecolor">
                                                <tr><td width="100%">
                                            <!--//////////////////////////////////////////-->
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                                <tr>
                                                    <td>
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                                            <tr>
                                                                <td width="80%" align="left"><!--Tempat menu-->
                                                                    <!--<div class="page">-->
                                                                    PAGE : 
                                                                    <%
                                                                        if(start>=recordToGet){
                                                                            out.println("<span class='pagelink2'><a href='javascript:cmdListFirst()'>&#124;&lt;</a></span>");
                                                                            out.println("<span class='pagelink2'><a href='javascript:cmdListPrev()'>&lt;&lt;</a></span>");
                                                                        }

                                                                        for(int i=start;i<(vectSize>(recordToGet+start)?recordToGet+start:vectSize+1);i++){
                                                                            int iPage = i;

                                                                            if(currPage==iPage){
                                                                                //out.println("<span class='pageSelected'>["+(iPage+1)+"]</span>");
                                                                                out.println("<span class='pagelink2'><a href='javascript:cmdGoToPage("+iPage+")'>["+(iPage+1)+"]</a></span>");
                                                                            }else{
                                                                                out.println("<span class='pagelink2'><a href='javascript:cmdGoToPage("+iPage+")'>["+(iPage+1)+"]</a></span>");
                                                                            }
                                                                        }
                                                                        if(iCommand==Command.GOTO){
                                                                            out.println("<span class='pageSelected2'>["+(vectSize+2)+"]</span>");
                                                                        }
                                                                        if(start+recordToGet<vectSize){
                                                                           // out.println("<a href='javascript:cmdListNext()'>[&gt;&gt;]</a>");
                                                                           // out.println("<a href='javascript:cmdListLast()'>[&gt;&#124;]</a>");
                                                                            out.println("<span class='pagelink2'><a href='javascript:cmdListNext()'>&gt;&gt;</a></span>");
                                                                            out.println("<span class='pagelink2'><a href='javascript:cmdListLast()'>&gt;&#124;</a></span>");
                                                                        }
                                                                        out.println("<span class='pageSelected2'>Approval</span>");
                                                                        out.println("    "+msg);
                                                                    %>
                                                                <!--</div>-->
                                                                </td>
                                                                <td width="80%" align="right">
                                                                    <%
                                                                   // if(currPage!=0){
                                                                    out.println("<span class='pagelink2'><a href='javascript:cmdApprove()'>Save</a></span>");
                                                                    //}
                                                                    %>
                                                                </td>
                                                            <tr>
                                                        </table>
                                                    </td>
                                                <tr>
                                            </table>
                                            <table  width="100%" border="0" cellspacing="1" cellpadding="1">
                                                <!--<tr>
                                                    <td>
                                                        <br><br>
                                                        <b>
                                                            Ensure the above Individual Training & Development Plan meets promotional recommendations.
                                                            <i>
                                                                <br>(Pastikan bahwa training pribadi & Rancana Pengembangan sesuai dengan rekomendasi promosi)
                                                            </i><br><br>
                                                        </b>
                                                    </td>
                                                </tr>-->
                                                <tr>
                                                    <td><br><br><br>
                                                        <%
                                                        
                                                        
                                                        Employee employee = new Employee();
                                                        Employee assessor = new Employee();
                                                        Employee divHead = new Employee();
                                                        if(appraisalMain.getOID()>0){
                                                            try{
                                                                employee = PstEmployee.fetchExc(appraisalMain.getEmployeeId());
                                                                assessor = PstEmployee.fetchExc(appraisalMain.getAssesorId());
                                                                divHead = PstEmployee.fetchExc(appraisalMain.getDivisionHeadId());
                                                            }catch(Exception ex){}
                                                        }
                                                        
                                                        %>
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                                            <tr>
                                                                <td align="center" width="33%" valign="top"></td>
                                                                <td align="center" width="33%"  valign="top"></td>
                                                                <td align="center" width="33%"  valign="top"><b>Noted by:<br><i>(Dicatat oleh:)</i></b></td>
                                                            </tr>
                                                            <tr>
                                                                <td align="center"  valign="bottom"><b><u><h2>(<%=employee.getFullName()%>)</h2></u></b></td>
                                                                <td align="center"  valign="bottom"><b><u><h2>(<%=assessor.getFullName()%>)</h2></u></b></td>
                                                                    
                                                                    <td align="center"  valign="bottom"><b>
                                                                        
                                                                    <%												  
                                                                       //   if(oidAppraisalMain!=0)
                                                                          {
                                                                                  Vector divHeadKey = new Vector(1,1);
                                                                                  Vector divHeadValue = new Vector(1,1);

                                                                                  divHeadKey.add("Select Department Head");
                                                                                  divHeadValue.add("0");

                                                                                  String selectedApproval = ""+divHead.getOID();
                                                                                  if(depHeadAuthorize)
                                                                                  {
                                                                                          selectedApproval = ""+divHead.getOID();
                                                                                  }

                                                                                  Vector vectPositionLvl1 = new Vector(1,1);
                                                                                  vectPositionLvl1.add(""+PstPosition.LEVEL_SECRETARY);
                                                                                  vectPositionLvl1.add(""+PstPosition.LEVEL_SUPERVISOR);
                                                                                  vectPositionLvl1.add(""+PstPosition.LEVEL_MANAGER);   
                                                                                  vectPositionLvl1.add(""+PstPosition.LEVEL_GENERAL_MANAGER);														           
                                                                                  
                                                                                  Vector listDivHead = SessEmployee.listEmployeeByPositionLevel(employee, vectPositionLvl1);
                                                                                  
                                                                                  String OidExecutiveOffice = String.valueOf(PstSystemProperty.getValueByName("OID_DEPARTMENT_EXECUTIVE_OFFICE"));
                                                                                  
                                                                                  Vector listDivHeadExecutifeOffice = new Vector();
                                                                                  
                                                                                  if(employee.getDepartmentId()!=Long.parseLong(OidExecutiveOffice)){
                                                                                      
                                                                                    listDivHeadExecutifeOffice = SessEmployee.listEmployeeByPositionLevelExecutiveOffice(Long.parseLong(OidExecutiveOffice)); 
                                                                                  
                                                                                  }
                                                                                  
                                                                                  for(int i=0; i<listDivHead.size(); i++)
                                                                                  {
                                                                                          Employee objEmp = (Employee)listDivHead.get(i);

                                                                                          if(employee.getOID() != objEmp.getOID())
                                                                                          {
                                                                                                  divHeadKey.add(objEmp.getFullName());
                                                                                                  divHeadValue.add(""+objEmp.getOID());
                                                                                          }
                                                                                  }
                                                                                  
                                                                                  for(int a=0; a<listDivHeadExecutifeOffice.size(); a++){
                                                                                          Employee objEmpExecutive = (Employee)listDivHeadExecutifeOffice.get(a);

                                                                                          if(employee.getOID() != objEmpExecutive.getOID())
                                                                                          {
                                                                                                  divHeadKey.add(objEmpExecutive.getFullName());
                                                                                                  divHeadValue.add(""+objEmpExecutive.getOID());
                                                                                          }
                                                                                  }
                                                                                  
                                                                                  String strAttribute = "class=\"formElemen\" onChange=\"javascript:checkApproval()\"";
                                                                                  out.println(ControlCombo.draw("EMP_FULLNAME", null, selectedApproval, divHeadValue, divHeadKey, strAttribute));														  
                                                                          }
                                                                        //  else
                                                                        //  {
                                                                        //        out.println("&nbsp;");
                                                                         // }													  		  
                                                                  %> 
                                                          </b><br></td>
                                                            </tr>
                                                            <tr>
                                                                <td align="center"  valign="top"><b>Signature <br>Band Member<br><i>(Band Member)</i></b></td>
                                                                <td align="center"  valign="top"><b>Signature <br>Assessor<br><i>(Penilai)</i></b></td>
                                                                <td align="center"  valign="top"><b>Signature <br>*Division Head<br><i>(Kepala Divisi)</i></b><br><br></td>
                                                            </tr>
                                                            
                                                            <tr>
                                                                <%
                                                                String strPathEmp = "";
                                                                String strPathAss = "";
                                                                String strPathDiv = ""; 
                                                                ImageAssign imgEmp = PstImageAssign.getImageAssignByEmp(employee.getOID());
                                                                ImageAssign imgAss = PstImageAssign.getImageAssignByEmp(assessor.getOID());
                                                                ImageAssign imgDiv = PstImageAssign.getImageAssignByEmp(divHead.getOID());
                                                                String pathImgAssign = approot;
                                                                if(imgEmp.getPath()!=null && imgEmp.getPath().length()>0){
                                                                    strPathEmp = "<img name='empAssign' src='"+pathImgAssign+"/images/imgassign/"+imgEmp.getPath()+"' alt='' border='0' height='100' width='150'>";
                                                                }
                                                                if(imgAss.getPath()!=null && imgAss.getPath().length()>0){
                                                                    strPathAss = "<img name='assAssign' src='"+pathImgAssign+"/images/imgassign/"+imgAss.getPath()+"' alt='' border='0' height='100' width='150'>";
                                                                }
                                                                if(imgDiv.getPath()!=null && imgDiv.getPath().length()>0){
                                                                    strPathDiv = "<img name='divAssign' src='"+pathImgAssign+"/images/imgassign/"+imgDiv.getPath()+"' alt='' border='0' height='100' width='150'>";
                                                                }
                                                                
                                                                %>
                                                                <td align="center"  valign="top"><%=strPathEmp%></td>
                                                                <td align="center"  valign="top"><%=strPathAss%></td>
                                                                <td align="center"  valign="top"><%=strPathDiv%></td>
                                                            </tr>
                                                            
                                                            <tr>
                                                                <td align="center"  valign="bottom">
                                                                    <%=ControlDatePopup.writeDate(FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_EMP_SIGN_DATE],appraisalMain.getEmployeeSignDate())%>
                                                                </td>
                                                                <td align="center"  valign="bottom">
                                                                    <%=ControlDatePopup.writeDate(FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_ASS_SIGN_DATE],appraisalMain.getAssessorSignDate())%>
                                                                </td>
                                                                <td align="center"  valign="bottom">
                                                                    <%=ControlDatePopup.writeDate(FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DIV_HEAD_SIGN_DATE],appraisalMain.getDivisionHeadSignDate())%>
                                                                </td>
                                                            </tr>
                                                            
                                                            <tr>
                                                                <td align="center"  valign="top"><b>Date<i>(Tanggal)</i></b></td>
                                                                <td align="center"  valign="top"><b>Date<i>(Tanggal)</i></b></td>
                                                                <td align="center"  valign="top"><b>Date<i>(Tanggal)</i></b><br><br></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <b>
                                                            Note: If the assessor were the Division Head, the General Manager wouldsign the <u>Noted by</u> corner.
                                                        </b>
                                                        <i>
                                                            <br>*Catatan: Jika penilai adalah Kepala Divisi, General Manager akan menandatangani disebelah tandatangan division head.
                                                        </i>
                                                    </td>
                                                </tr>
                                            </table>
                                            <!--//////////////////////////////////////////-->
                                                </td></tr>
                                            </table>
                                            </td></tr>
                                            
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </td> 
                                  </tr>
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
      </table>
		  </td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> 	  
      <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> 
<!-- #EndTemplate --></html>
