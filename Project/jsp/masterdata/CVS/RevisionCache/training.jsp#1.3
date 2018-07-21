
<% 
/* 
 * Page Name  		:  training.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.system.entity.PstSystemProperty" %>
<%@ page import = "com.dimata.util.blob.*" %>

<%@ include file = "../main/javainit.jsp" %>

<% 

    int appObjCodeGen = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_GENERAL_TRAINING); 
    int appObjCodeDept = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_DEPARTMENTAL_TRAINING); 
    int appObjCode = 0; 
    
    // check training privilege (0 = none, 1 = general, 2 = departmental)
    int trainType = checkTrainingType(appObjCodeGen, appObjCodeDept, userSession);
    
    if(trainType == PRIV_GENERAL) {    
        appObjCode = appObjCodeGen;
    } 
    else if(trainType == PRIV_DEPT) {  
        appObjCode = appObjCodeDept;
    }

    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%@ include file = "../main/checktraining.jsp" %>


<!-- Jsp Block -->
<%!
	public String drawList(Vector objectClass ,  long trainingId, Vector vctDepartment)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("0");
		ctrlist.setListStyle("tblStyle");
                ctrlist.setTitleStyle("title_tbl");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("title_tbl");
                ctrlist.setCellSpacing("0");
		ctrlist.addHeader("Name","");
		ctrlist.addHeader("Description","");
		//ctrlist.addHeader("Department","30%");
                ctrlist.addHeader("Type", "");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
	
		for (int i = 0; i < objectClass.size(); i++) {
			 Vector temp = (Vector)objectClass.get(i);
			 Training training = (Training)temp.get(0);
			 Vector rowx = new Vector();
			 if(trainingId == training.getOID())
				 index = i;
                         
                         TrainType type = new TrainType();
                         
                         try {
                             type = PstTrainType.fetchExc(training.getType());
                         }
                         catch(Exception e) {
                             type = new TrainType();
                         }

			rowx.add(training.getName());
			rowx.add(training.getDescription());			
			//rowx.add(PstTraining.getTrainingDepartment(training.getOID(), vctDepartment));
                        rowx.add(type.getTypeName());

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(training.getOID()));
		}
		return ctrlist.draw(index);
	}
	
	public String chekOID(long oid, Vector vct){
		if(vct!=null && vct.size()>0){
			for(int i=0; i<vct.size(); i++){
				TrainingDept td = (TrainingDept)vct.get(i);
				if(td.getDepartmentId() == oid){
					return "checked";
				}
			}
		}
		return "";
	}
%>
<%!

        public String drawListTrainingFile(Vector objectClass,long  trainingID)
	{
		
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("50%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("<a style=\"text-decoration:none\" href =\"javascript:cmdDelete()\"><font color=\"#30009D\">Delete</font></a>","2%", "2", "0");
		ctrlist.addHeader("FileName","30%");
		ctrlist.addHeader("Upload File","20%");
	
		ctrlist.setLinkRow(1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdOpen('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
	
			
		for (int i = 0; i < objectClass.size(); i++) {
			TrainingFile trainingFile = (TrainingFile)objectClass.get(i);
			 Vector rowx = new Vector();
			rowx.add("<input type=\"checkbox\" name=\"delete"+i+"\" value=\""+trainingFile.getOID()+"\" class=\"formElemen\" size=\"10\"><input type=\"hidden\" name=\"trainingFileId\" value=\""+trainingFile.getOID()+"\" class=\"formElemen\" size=\"10\">");
			rowx.add(trainingFile.getFileName());
			rowx.add("<img border=\"0\" src=\"../images/BtnNew.jpg\" width=\"20\" height=\"20\" ><div  valign =\"top\" align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdAttach('"+trainingFile.getOID()+"','"+trainingID+"')\"><font color=\"#30009D\">Upload File</font></a></div>");
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(trainingFile.getFileName()));
		}
		
		return ctrlist.draw(index);
	}


%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidTraining = FRMQueryString.requestLong(request, "hidden_training_id");
String pathFileTraining = PstSystemProperty.getValueByName("TRAINING_MATERIAL");
String pathFileTrainingShort = PstSystemProperty.getValueByName("TRAINING_MATERIAL_SHORT");
String trainingName = FRMQueryString.requestString(request, "hidden_training_name");
String description = FRMQueryString.requestString(request, "hidden_description");
int orderBy = FRMQueryString.requestInt(request, "hidden_sort_by");

HttpSession sessEmpPresence = request.getSession(true);
Vector vctDailyAbsenteeism = (Vector)sessEmpPresence.getValue("SESS_TRAINING");

Vector vctx = new Vector();
vctx.add(trainingName);
vctx.add(description);
vctx.add(""+orderBy);
session.putValue("SESS_SRC_TRAINING", vctx);

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

Vector vctDepartment = PstDepartment.list(0,0, "", PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);

CtrlTraining ctrlTraining = new CtrlTraining(request);
ControlLine ctrLine = new ControlLine();
Vector listTraining = new Vector(1,1);

String[] depid = request.getParameterValues("dep_id");

/*switch statement */
iErrCode = ctrlTraining.action(iCommand , oidTraining, depid);
/* end switch*/
FrmTraining frmTraining = ctrlTraining.getForm();

if(iCommand == Command.SAVE){
    if(depid != null && depid.length >0){
    for(int j=0;j<depid.length;j++){
        boolean isEnable = false;
        for(int i=0;i<vctDailyAbsenteeism.size();i++){
            Department dept = (Department)vctDailyAbsenteeism.get(i);
            if(depid[j].equals(""+dept.getOID())){
                isEnable = true;
                break;
            }            
        }
        if(!isEnable){
            try{
                long depOid = Long.parseLong(depid[j]);
                Department dept = PstDepartment.fetchExc(depOid);
                vctDailyAbsenteeism.add(dept);
                System.out.println(dept.getOID()+" ::: "+dept.getDepartment());
            }catch(Exception ex){}
            
        }
    }
    sessEmpPresence.putValue("SESS_TRAINING", vctDailyAbsenteeism);
    }
}
    
/*count list All Training*/
int vectSize = PstTraining.countTrainingMaster(trainingName,description,vctDailyAbsenteeism);

Training training = ctrlTraining.getTraining();
msgString =  ctrlTraining.getMessage();
if(iCommand == Command.SAVE){
    if(training.getOID()>0){
        oidTraining = training.getOID();
    }
}
if(iCommand == Command.BACK){
    oidTraining = 0;
}

String whereClause1 = PstTrainingDept.fieldNames[PstTrainingDept.FLD_TRAINING_ID]+"="+training.getOID();
Vector vctTrDept = PstTrainingDept.list(0,0, whereClause1, null);

// list Material Trainining
String whClause = PstTrainingFile.fieldNames[PstTrainingFile.FLD_TRAINING_ID]+"="+oidTraining;
Vector vectTrainingFile = PstTrainingFile.list(0,0,whClause,"");


/*switch list Training*/

/*if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))
	start = PstTraining.generateFindStart(training.getOID(),recordToGet, whereClause);
*/

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST || iCommand == Command.LIST)){
		start = ctrlTraining.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */

if(trainType == PRIV_DEPT)
    listTraining = PstTraining.searchTrainingMaster(trainingName,description,vctDailyAbsenteeism,start,recordToGet,orderBy, PRIV_DEPT);
else
    listTraining = PstTraining.searchTrainingMaster(trainingName,description,vctDailyAbsenteeism,start,recordToGet,orderBy, 0);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listTraining.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
                 start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
         
	 if(trainType == PRIV_DEPT)
            listTraining = PstTraining.searchTrainingMaster(trainingName,description,vctDailyAbsenteeism,start,recordToGet,orderBy, PRIV_DEPT);
        else
            listTraining = PstTraining.searchTrainingMaster(trainingName,description,vctDailyAbsenteeism,start,recordToGet,orderBy, 0);

}

// untuk delete
if (iCommand == Command.SUBMIT) {
 	long  oidTrainingMaterial=0;
	String[] training_material_id = null;
        
	try {
                training_material_id = request.getParameterValues("trainingFileId");
        }
        catch (Exception e) 
        {
                System.out.println("Err : "+e.toString());
        }
		
        for (int i = 0; i < vectTrainingFile.size(); i++) 
        {
                try 
                        {
                           oidTrainingMaterial = FRMQueryString.requestLong(request, "delete"+i+""); // row yang dicheked
                        } catch (Exception e) 
                        {
                                System.out.println("err get checked value"+e.toString());
                        }

                if(oidTrainingMaterial!=0){
                        PstTrainingFile.deleteExc(Long.parseLong(training_material_id[i]));
                }
        }
			
        response.sendRedirect("training.jsp?command="+Command.EDIT+"&hidden_training_id="+oidTraining);
 }

%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Harisma - Master Training List</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmtraining.hidden_training_id.value="0";
	document.frmtraining.command.value="<%=Command.ADD%>";
	document.frmtraining.prev_command.value="<%=prevCommand%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdAsk(oidTraining){
	document.frmtraining.hidden_training_id.value=oidTraining;
	document.frmtraining.command.value="<%=Command.ASK%>";
	document.frmtraining.prev_command.value="<%=prevCommand%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdConfirmDelete(oidTraining){
	document.frmtraining.hidden_training_id.value=oidTraining;
	document.frmtraining.command.value="<%=Command.DELETE%>";
	document.frmtraining.prev_command.value="<%=prevCommand%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdSave(){
	document.frmtraining.command.value="<%=Command.SAVE%>";
	document.frmtraining.prev_command.value="<%=prevCommand%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdEdit(oidTraining){
	document.frmtraining.hidden_training_id.value=oidTraining;
	document.frmtraining.command.value="<%=Command.EDIT%>";
	document.frmtraining.prev_command.value="<%=prevCommand%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}
	
function cmdCancel(oidTraining){
	document.frmtraining.hidden_training_id.value=oidTraining;
	document.frmtraining.command.value="<%=Command.EDIT%>";
	document.frmtraining.prev_command.value="<%=prevCommand%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdBack(){
	document.frmtraining.command.value="<%=Command.BACK%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.hidden_training_id="";
	document.frmtraining.submit();
}
	
function cmdBackSearch(){
	document.frmtraining.command.value="<%=Command.BACK%>";
	document.frmtraining.action="srctraining.jsp";
	document.frmtraining.submit();
}

function cmdListFirst(){
	document.frmtraining.command.value="<%=Command.FIRST%>";
	document.frmtraining.prev_command.value="<%=Command.FIRST%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdListPrev(){
	document.frmtraining.command.value="<%=Command.PREV%>";
	document.frmtraining.prev_command.value="<%=Command.PREV%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdListNext(){
	document.frmtraining.command.value="<%=Command.NEXT%>";
	document.frmtraining.prev_command.value="<%=Command.NEXT%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdListLast(){
	document.frmtraining.command.value="<%=Command.LAST%>";
	document.frmtraining.prev_command.value="<%=Command.LAST%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdDelete(){
	document.frmtraining.command.value="<%=Command.SUBMIT%>";
	document.frmtraining.prev_command.value="<%=Command.SUBMIT%>";
	document.frmtraining.action="training.jsp";
	document.frmtraining.submit();
}

function cmdUpload(oidTraining){
	document.frmtraining.command.value="<%=Command.EDIT%>";
	document.frmtraining.action="upload_training_material.jsp?command="+<%=Command.EDIT%>+"&training_id=" + oidTraining;
	document.frmtraining.submit();
}

function cmdAttach(oidTrainingFile,oidTraining){
	document.frmtraining.command.value="<%=Command.EDIT%>";
	document.frmtraining.action="upload_training_material.jsp?command="+<%=Command.EDIT%>+"&training_file_id=" + oidTrainingFile+ "&training_id=" + oidTraining;
	document.frmtraining.submit();
}

function cmdOpen(fileName){
    
       window.open("<%=approot%>" + "<%=pathFileTrainingShort%>"+fileName , null);
		
}

function setChecked(val) {
	dml=document.frmtraining;
	len = dml.elements.length;
	var i=0;
	for( i=0 ; i<len ; i++) {						
		dml.elements[i].checked = val;					
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
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
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
<style type="text/css">
    .tblStyle {border-collapse: collapse;font-size: 11px;}
    .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
</style>
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../main/mnmain.jsp" %>
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
                  Master Data &gt; Recruitment &gt; Training Master Data<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="tablecolor"  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmtraining" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_training_id" value="<%=oidTraining%>">
                                      <input type="hidden" name="hidden_training_name" value="<%=trainingName%>">
                                      <input type="hidden" name="hidden_description" value="<%=description%>">
                                      <input type="hidden" name="hidden_sort_by" value="<%=orderBy%>">
									  
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" colspan="3">&nbsp; 
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Training 
                                                  Master List </td>
                                              </tr>
                                              <%
                                                try{
                                                        if (listTraining!=null && listTraining.size()>0){
                                                %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
												
                                                  <%= drawList(listTraining, oidTraining, vctDepartment)%> 
												  
                                                </td>
                                                </tr>
                                              <%  }else{
											  %>
                                                  <tr align="left" valign="top"> 
                                                    <td height="22" valign="middle" colspan="3"> 
                                                  <span class="comment">&nbsp;List is empty ...</span>
                                                  </td></tr>
                                                  <%} 
                                              }catch(Exception exc){ 
                                                    out.println(exc.toString());
                                              }%>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                  <span class="command"> 
                                                  <% 
                                                       int cmd = 0;
                                                               if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
                                                                    (iCommand == Command.NEXT || iCommand == Command.LAST))
                                                                            cmd =iCommand; 
                                                       else{
                                                              if(iCommand == Command.NONE || prevCommand == Command.NONE)
                                                                    cmd = Command.FIRST;
                                                              else 
                                                                    cmd =prevCommand; 
                                                       } 
                                                    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                     %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span> </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3">
												
                                                 <%if(iCommand!=Command.ADD && iCommand!=Command.ASK && iCommand!=Command.SAVE && iCommand!=Command.EDIT && iErrCode==FRMMessage.NONE){%>
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr>  
                                                      <td width="2"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24">
                                                          <% if(privAdd){ %>
                                                          <a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a>
                                                          <% } %>
                                                      </td>
                                                      <td width="2"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New Training Master</a></td>
                                                    						
                                                      <td width="10"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdBackSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="10"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="454"><a href="javascript:cmdBackSearch()" class="command">Back to Search Training</a></td>
											 
                                                    </tr>
                                                  </table>
                                                  <%}%>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||iCommand==Command.SAVE ||(iErrCode!=FRMMessage.NONE)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="97%" border="0" cellspacing="1" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="middle" colspan="3"><b>___Training 
                                                  Master Editor</b></td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="middle" width="96">&nbsp;</td>
                                                <td height="21" colspan="2" class="comment">*)= 
                                                  required</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="96">Training 
                                                  Name</td>
                                                <td height="21" colspan="2"> 
                                                  <input type="text" name="<%=frmTraining.fieldNames[FrmTraining.FRM_FIELD_NAME] %>"  value="<%= training.getName() %>" class="formElemen" size="50">
                                                  * <%= frmTraining.getErrorMsg(FrmTraining.FRM_FIELD_NAME) %> 
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="96">Training 
                                                  Type</td>
                                                <td height="21" colspan="2"> 
                                                   <%
                                                        Vector type_val = new Vector();
                                                        Vector type_key = new Vector();
                                                        
                                                        String order = PstTrainType.fieldNames[PstTrainType.FLD_TRAIN_TYPE_NAME];
                                                        Vector listTrainType = PstTrainType.list(0, 0, "", order);
                                                       
                                                        for(int i=0; i<listTrainType.size(); i++) {
                                                            TrainType train = (TrainType)listTrainType.get(i);
                                                            type_key.add(train.getTypeName());
                                                            type_val.add(String.valueOf(train.getOID()));
                                                        }
                                                    %>
                                                    <%= ControlCombo.draw(frmTraining.fieldNames[FrmTraining.FRM_FIELD_TYPE],"formElemen",null, ""+training.getType(), type_val, type_key) %>
                                                    * <%= frmTraining.getErrorMsg(FrmTraining.FRM_FIELD_TYPE) %>
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="96">Description</td>
                                                <td height="21" colspan="2"> 
                                                  <textarea name="<%=frmTraining.fieldNames[FrmTraining.FRM_FIELD_DESCRIPTION] %>" class="formElemen" cols="50" rows="3"><%= training.getDescription() %></textarea>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="top" width="96">&nbsp;</td>
                                                <td height="8" colspan="2">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="top" width="96"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td height="8" colspan="2"> 
                                                
                                                <% if(trainType == PRIV_DEPT) { %>
                                                
                                                    <input type="hidden" name="dep_id" value="<%= departmentOid %>"><%= departmentOfLoginUser.getDepartment() %>
                                           
                                                <% } else if(trainType == PRIV_GENERAL) { 
                                                            
                                                   if(vctDepartment!=null && vctDepartment.size()>0){
                                                      int itr = vctDepartment.size() / 3;

                                                      if(vctDepartment.size() % 3 > 0){
                                                        itr = itr+1;
                                                      }

                                                      int index = 0;
                                                      %>
                                                      
                                                      <table width="98%" cellpadding="0" cellspacing="0">
                                                          
                                                      <%for(int i=0; i<itr; i++) {
														
                                                            if(i > 0){
                                                                index = (3*i);
                                                            }
                                                        %>
                                                        
                                                        <tr> 
                                                        <td width="4%"> 
                                                          
                                                          <%
                                                              Department dep1 = new Department();
                                                              String chk = "";

                                                              if(index < vctDepartment.size()) {
                                                                    dep1 = (Department)vctDepartment.get(index);
                                                                    chk = chekOID(dep1.getOID(), vctTrDept);
                                                                    %>
                                                                    <input type="checkbox" name="dep_id" value="<%=dep1.getOID()%>" <%=chk%>>
                                                           <% } // end if %>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        <%if(index < vctDepartment.size()){%>
                                                            <%=dep1.getDepartment()%> 
                                                        <%}%>
                                                      </td>
                                                      <td width="4%"> 
                                                            <%
                                                              Department dep2 = new Department();
                                                              index = index +  1;
                                                              if(index < vctDepartment.size()){
                                                                    dep2 = (Department)vctDepartment.get(index);
                                                                    chk = chekOID(dep2.getOID(), vctTrDept);
                                                                    %>
                                                                    <input type="checkbox" name="dep_id" value="<%=dep2.getOID()%>" <%=chk%>>
                                                            <% } // end if %>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        <%if(index < vctDepartment.size()){%>
                                                            <%=dep2.getDepartment()%> 
                                                        <%}%>
                                                      </td>
                                                      <td width="3%"> 
                                                            <%
                                                              Department dep3 = new Department();
                                                              index = index + 1;
                                                              if(index < vctDepartment.size()){
                                                                    dep3 = (Department)vctDepartment.get(index);
                                                                    chk = chekOID(dep3.getOID(), vctTrDept);
                                                                    %>
                                                                    <input type="checkbox" name="dep_id" value="<%=dep3.getOID()%>" <%=chk%>>
                                                            <% } // end if %>
                                                      </td>
                                                      <td width="31%" nowrap> 
                                                        <%if(index < vctDepartment.size()){%>
                                                            <%=dep3.getDepartment()%> 
                                                        <%}%>
                                                      </td>
                                                    </tr>
                                                    <%}%>
                                                  </table>
                                                <% } else { %>
                                                   <span class="comment">no department 
                                                   available ...</span> 
                                                <% } %>                                                  
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="96">&nbsp;</td>
                                                <td height="8" colspan="2">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="96">&nbsp;</td>
                                                <td height="8" colspan="2"><a href="javascript:setChecked(1)">General 
                                                  Training( Select All Department 
                                                  )</a> &nbsp;&nbsp;| &nbsp;&nbsp;<a href="javascript:setChecked(0)">Release 
                                                  All</a></td>                                              
                                              </tr>                                             
                                              <% } // end if %>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="96"></td>
                                                <td height="8" colspan="2"></td>
                                              </tr>
											  <% 										  %>
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="96">Training Material</td>
												<%
													if(vectTrainingFile!=null && vectTrainingFile.size() > 0){
													%>
													<td height="8" colspan="2">
													<%
														out.println(drawListTrainingFile(vectTrainingFile,oidTraining));
													%></td>
												   <%
													}else{
												%>
												   <td height="8" colspan="2"><i>No Training material found....</i></td>
												<%
												 }
												%>
												</tr>
												<tr align="left" valign="top"> 
													<td height="8" valign="middle" width="96"></td>
													<td height="8" colspan="2"><a href="javascript:cmdUpload('<%=oidTraining%>')">Add Training Material</a></td>
												
                                              </tr>
											  <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="96"></td>
                                                <td height="8" colspan="2"></td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="3" class="command"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidTraining+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidTraining+"')";
									String scancel = "javascript:cmdEdit('"+oidTraining+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
										ctrLine.setDeleteCaption("Delete");
										ctrLine.setSaveCaption("Save");
										ctrLine.setAddCaption("Add new");
										ctrLine.setDeleteCaption("Delete");

									if (privDelete){
										ctrLine.setConfirmDelCommand(sconDelCom);
										ctrLine.setDeleteCommand(scomDel);
										ctrLine.setEditCommand(scancel);
										
									}else{ 
										ctrLine.setConfirmDelCaption("");
										ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
										
									}
									
									if(privAdd == false  && privUpdate == false){
										ctrLine.setSaveCaption("");
									}
									
									if (privAdd == false){
										ctrLine.setAddCaption("");
										
										
									}
									%>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
												
                                              </tr>
                                              <tr> 
                                                <td width="96">&nbsp;</td>
                                                <td width="645">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="3"> 
                                                  <div align="left"></div>
                                                </td>
                                              </tr>
                                            </table>
                                            <%}%>
                                          </td>
                                        </tr>
                                      </table>
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
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
