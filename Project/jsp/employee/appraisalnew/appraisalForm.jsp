 
<%@page import="com.dimata.harisma.form.employee.assessment.FrmAssessmentFormItem"%>
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
        
        
        String msg = "";
	String errMsg = "";
        int iErrCode = FRMMessage.ERR_NONE;
	String whereClause = "";
	String orderClause = "";
        
        /*count list All Page*/
        AssessmentFormMain assMain = SessAssessmentMain.getAssessment(oidAppraisalMain);
        int vectSize = SessAssessmentFormItem.getMaxPage(assMain.getOID());
        
	if(!(needSaving)){needSaving=false;}

      //  System.out.println("----------------------------------->"+needSaving);
        if(iCommand==Command.SAVE && needSaving){
            //System.out.println("---------------------<><><<<<");
            ///////////////////////////////////////////////////////////////////            
            Vector vSection = new Vector(1,1);
            vSection=SessAssessmentFormSection.getSections(prevPage, assMain.getOID());
            Hashtable hAppraisal = new Hashtable();
            hAppraisal = SessAppraisal.listAppraisal(oidAppraisalMain);
                                   
            if(vSection.size()>0){
                for(int k=0;k<vSection.size();k++){
                    //float totalRating = 0;
                    AssessmentFormSection assSection = new AssessmentFormSection();
                    assSection = (AssessmentFormSection)vSection.get(k);       
                    Vector vEvalPoint = assSection.getPointEvaluationId()!=0?PstEvaluation.list(0, 30, PstEvaluation.fieldNames[PstEvaluation.FLD_EVAL_TYPE]+"="+ assSection.getPointEvaluationId(), ""): null;
                    Vector vEvalPredicate = assSection.getPredicateEvaluationId() !=0?PstEvaluation.list(0, 30, PstEvaluation.fieldNames[PstEvaluation.FLD_EVAL_TYPE]+"="+ assSection.getPredicateEvaluationId(), ""): null;
                                   
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
                                float tempRealization  = FRMQueryString.requestFloat(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_REALIZATION]+assItem.getOID());
                                String tempEvidence= FRMQueryString.requestString(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EVIDENCE]+assItem.getOID());
                                float tempPoint  = Evaluation.checkPoint(tempRealization, vEvalPoint) ;//FRMQueryString.requestDouble(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_POINT]+assItem.getOID());
                                float tempWeight  = FRMQueryString.requestFloat(request, FrmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_WEIGHT_POINT]+assItem.getOID());    
                                float tempRating = (tempPoint !=0 && tempWeight!=0) ? (tempPoint * tempWeight) : (float) FRMQueryString.requestDouble(request, FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING]+assItem.getOID()); 
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
                                appTemp.setRealization(tempRealization);
                                appTemp.setEvidence(tempEvidence);                                                                
                                appTemp.setPoint(tempPoint);
                                appTemp.setRating(tempRating);
                                //totalRating= totalRating + tempRating;
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
            //Update rating dan max item
            SessAppraisal.updateRating(oidAppraisalMain);
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
        
        
        
        if(oidAppraisalMain>0){
            try{
            if(currPage==0){
                Vector vAppData = new Vector(1,1);
                AppraisalMain objAppMain = new AppraisalMain();                
                    objAppMain = PstAppraisalMain.fetchExc(oidAppraisalMain);                
                strViewForm = ControlFormAppraisal.createFormMain(assMain, objAppMain);
            }else{
                strViewForm = ControlFormAppraisal.createPage(assMain.getOID(), currPage, oidAppraisalMain);
            }
           }catch(Exception ex){System.out.println(ex);}                      
        }
        
        
        if(prevCommand==Command.APPROVE && needSavingApproval){
            AppraisalMain appraisalMain = new AppraisalMain();
            try{
                appraisalMain = PstAppraisalMain.fetchExc(oidAppraisalMain);
            }catch(Exception ex){
            }
            long divHeadOid = FRMQueryString.requestLong(request,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DIVISION_HEAD_ID]);
            Date empSignDate = FRMQueryString.requestDate(request,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_EMP_SIGN_DATE]);
            Date assSignDate = FRMQueryString.requestDate(request,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_ASS_SIGN_DATE]);
            Date divSignDate = FRMQueryString.requestDate(request,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DIV_HEAD_SIGN_DATE]);
            appraisalMain.setDivisionHeadId(divHeadOid);
            appraisalMain.setEmployeeSignDate(empSignDate);
            appraisalMain.setAssessorSignDate(assSignDate);
            appraisalMain.setDivisionHeadSignDate(divSignDate);
            try{
                long oidTemp = PstAppraisalMain.updateExc(appraisalMain);
                if(oidTemp==appraisalMain.getOID()){
                    msg = "<font color='white'>Save data success</font>";
                }else{
                    msg = "<font color='red'>Save data failed</font>";
                }
            }catch(Exception ex){
              System.out.println(ex);
            }
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
function cmdGoToPage(page){
    //alert(page);
    document.<%=FrmAppraisal.FRM_APPRAISAL%>.prevPage.value=document.<%=FrmAppraisal.FRM_APPRAISAL%>.currPage.value;
    document.<%=FrmAppraisal.FRM_APPRAISAL%>.currPage.value=page;
    document.<%=FrmAppraisal.FRM_APPRAISAL%>.command.value="<%=String.valueOf(Command.SAVE)%>";
    document.<%=FrmAppraisal.FRM_APPRAISAL%>.action="appraisalForm.jsp";
    document.<%=FrmAppraisal.FRM_APPRAISAL%>.submit();
}

function check_length(element,max)
{
    maxLen = max; // max number of characters allowed
    if (element.value.length >= maxLen) {
    // Alert message if maximum limit is reached.
    // If required Alert can be removed.
    var msg = "You have reached your maximum limit of characters allowed";
    alert(msg);
    // Reached the Maximum length so trim the textarea
    element.value = element.value.substring(0, maxLen);
    }
    else{ // Maximum length not reached so update the value of my_text counter
    //my_form.text_num.value = maxLen - element.value.length;
    }
}

function cmdSetNeedSaving(){
    val = document.<%=FrmAppraisal.FRM_APPRAISAL%>.needSaving.value;
  //  alert("NEED SAVE :::: "+val);
    if(val!="true"){
        document.<%=FrmAppraisal.FRM_APPRAISAL%>.needSaving.value="true";
    }
}

function cmdSave(){
        document.<%=FrmAppraisal.FRM_APPRAISAL%>.prevPage.value=document.<%=FrmAppraisal.FRM_APPRAISAL%>.currPage.value;
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.command.value="<%=String.valueOf(Command.SAVE)%>";
        document.<%=FrmAppraisal.FRM_APPRAISAL%>.needSaving.value="true";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.action="appraisalForm.jsp";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.submit();
	}

function cmdApproval(){
        document.<%=FrmAppraisal.FRM_APPRAISAL%>.prevPage.value=document.<%=FrmAppraisal.FRM_APPRAISAL%>.currPage.value;
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.command.value="<%=String.valueOf(Command.SAVE)%>";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.action="approvalForm.jsp";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.submit();
	}
         
function cmdBack(){
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.command.value="<%=String.valueOf(Command.BACK)%>";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.action="appraisalForm.jsp";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.submit();
	}

function cmdListFirst(){
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.action="appraisalForm.jsp";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.submit();
}

function cmdListPrev(){
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.command.value="<%=String.valueOf(Command.PREV)%>";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.action="appraisalForm.jsp";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.submit();
	}

function cmdListNext(){
        //alert("NEXT");
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.action="appraisalForm.jsp";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.submit();
}

function cmdListLast(){
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.command.value="<%=String.valueOf(Command.LAST)%>";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.action="appraisalForm.jsp";
	document.<%=FrmAppraisal.FRM_APPRAISAL%>.submit();
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
                              <form name="<%=FrmAppraisal.FRM_APPRAISAL%>" method="post" action="">
                                <input type="hidden" name="command" value="">
                                <input type="hidden" name="needSaving" value="<%=String.valueOf(needSaving)%>">
                                <input type="hidden" name="currPage" value="<%=String.valueOf(currPage)%>">
                                <input type="hidden" name="prevPage" value="<%=String.valueOf(prevPage)%>">
                                <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                <input type="hidden" name="prev_command" value="<%=String.valueOf(iCommand)%>">
                                <input type="hidden" name="employee_appraisal_oid" value="<%=String.valueOf(oidAppraisalMain)%>">
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
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
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
                                                                                out.println("<span class='pageSelected2'>["+(iPage+1)+"]</span>");
                                                                            }else{
                                                                                out.println("<span class='pagelink2'><a href='javascript:cmdGoToPage("+iPage+")'>["+(iPage+1)+"]</a></span>");
                                                                            }
                                                                        }
                                                                        if(iCommand==Command.GOTO){
                                                                            out.println("<span class='pageSelected'>["+(vectSize+2)+"]</span>");
                                                                        }
                                                                        if(start+recordToGet<vectSize){
                                                                           // out.println("<a href='javascript:cmdListNext()'>[&gt;&gt;]</a>");
                                                                           // out.println("<a href='javascript:cmdListLast()'>[&gt;&#124;]</a>");
                                                                            out.println("<span class='pagelink2'><a href='javascript:cmdListNext()'>&gt;&gt;</a></span>");
                                                                            out.println("<span class='pagelink2'><a href='javascript:cmdListLast()'>&gt;&#124;</a></span>");
                                                                        }
                                                                       if (privStart){
                                                                        out.println("<span class='pagelink2'><a href='javascript:cmdApproval()'>Approval</a></span>");
                                                                       } 
                                                                        out.println("    "+msg);
                                                                    %>
                                                                <!--</div>-->
                                                                </td>
                                                                <td width="80%" align="right">
                                                                    <%
                                                                    if(currPage!=0){
                                                                    out.println("<span class='pagelink2'><a href='javascript:cmdSave()'>Save</a></span>");
                                                                    }
                                                                    %>
                                                                </td>
                                                            <tr>
                                                        </table>
                                                    </td>
                                                <tr>
                                            </table>
                                        <%
                                        if(strViewForm.length()>0){
                                        %>    
                                            <%=strViewForm%>
                                        <%}%>
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
