<% 

/* 

 * Page Name  		:  medicalrecord.jsp

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

<%@ page import = "com.dimata.harisma.entity.clinic.*" %>

<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ page import = "com.dimata.harisma.form.clinic.*" %>

<%@ page import = "com.dimata.harisma.form.search.*" %>

<%@ page import = "com.dimata.harisma.entity.search.*" %>

<%@ page import = "com.dimata.harisma.session.clinic.*" %>

<%@ include file = "../../main/javainit.jsp" %>

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_MEDICAL, AppObjInfo.OBJ_MEDICAL_RECORD); %>

<%//@ include file = "../../main/checkuser.jsp" %>

<%

/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/

boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));

boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));

boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));

%>

<!-- Jsp Block -->

<%!

public String drawList(Vector objectClass, long medicalRecordId, String strMedRecDisc){

	ControlList ctrlist = new ControlList();

	ctrlist.setAreaWidth("100%");

	ctrlist.setListStyle("listgen");

	ctrlist.setTitleStyle("listgentitle");

	ctrlist.setCellStyle("listgensell");

	ctrlist.setHeaderStyle("listgentitle");	

	ctrlist.addHeader("Employee","15%");

	ctrlist.addHeader("Family Member","13%");

	ctrlist.addHeader("Disease Type","10%");

	ctrlist.addHeader("Medical Type","10%");

	ctrlist.addHeader("Record Date","10%");
	ctrlist.addHeader("Case/Treatment","10%");
	ctrlist.addHeader("Case Qty","2%");
	ctrlist.addHeader("Amount(Rp.)","8%");

        if((strMedRecDisc!=null ) && (strMedRecDisc.compareTo("1")==0)){
           ctrlist.addHeader("Discount(%)","7%");
           ctrlist.addHeader("Discount(Rp.)","7%");		
           }

	ctrlist.addHeader("Total(Rp.)","8%");



	ctrlist.setLinkRow(0);

	ctrlist.setLinkSufix("");

	Vector lstData = ctrlist.getData();

	Vector lstLinkData = ctrlist.getLinkData();

	Vector rowx = new Vector(1,1);

	ctrlist.reset();

	int index = -1;

	String whereCls = "";

	String orderCls = "";



	for (int i = 0; i < objectClass.size(); i++) {

		 MedicalRecord medicalRecord = (MedicalRecord)objectClass.get(i);

		 rowx = new Vector();

		 if(medicalRecordId == medicalRecord.getOID())

			 index = i; 



		 Employee employee = new Employee();

		 FamilyMember falMember = new FamilyMember();

		 DiseaseType diseaseType = new DiseaseType();

		 MedicalType medicalType = new MedicalType();

		 try{

			employee = PstEmployee.fetchExc(medicalRecord.getEmployeeId());

			if(medicalRecord.getFamilyMemberId()!=0){

				falMember = PstFamilyMember.fetchExc(medicalRecord.getFamilyMemberId());

			}

			diseaseType = PstDiseaseType.fetchExc(medicalRecord.getDiseaseTypeId());

			medicalType = PstMedicalType.fetchExc(medicalRecord.getMedicalTypeId());												

		 }catch(Exception exc){

		 	System.out.println("Exception med rec. get data : "+exc.toString());

		 }

		 

		rowx.add("<a href=\"javascript:cmdEdit('"+medicalRecord.getOID()+"')\">"+employee.getFullName()+"</a>");

		rowx.add(falMember.getFullName()); 			

		rowx.add(diseaseType.getDiseaseType());

		rowx.add(medicalType.getTypeName());

		String str_dt_RecordDate = ""; 

		try{

			Date dt_RecordDate = medicalRecord.getRecordDate();

			if(dt_RecordDate==null){

				dt_RecordDate = new Date();

			}



		str_dt_RecordDate = Formater.formatDate(dt_RecordDate, "dd MMM yyyy");

		}catch(Exception e){ str_dt_RecordDate = ""; }

		rowx.add(str_dt_RecordDate);
                MedicalCase medCase = new MedicalCase();
                try{
                    medCase = PstMedicalCase.fetchExc(medicalRecord.getMedicalCaseId());
                } catch(Exception exc){
                    System.out.println("PstMedicalCase.fetchExc("+medicalRecord.getMedicalCaseId()+") :" +exc);
                }
                rowx.add(medCase.getCaseName());
		rowx.add(Formater.formatNumber(medicalRecord.getCaseQuantity(),"#,###.#"));

		rowx.add(Formater.formatNumber(medicalRecord.getAmount(),"#,###.#"));

                if((strMedRecDisc!=null ) && (strMedRecDisc.compareTo("1")==0)){
                    rowx.add(Formater.formatNumber(medicalRecord.getDiscountInPercent(),"#,###.#"));
                    rowx.add(Formater.formatNumber(medicalRecord.getDiscountInRp(),"#,###.#"));
                  }  

		rowx.add(Formater.formatNumber(medicalRecord.getTotal(),"#,###.#"));						



		lstData.add(rowx);

	}

	return ctrlist.draw(index);

}

%>

<%
String strMedRecDisc = null; // set MED_REC_DISCOUNT    on system property 0 or not set= hide  1=show
try{
    strMedRecDisc = PstSystemProperty.getValueByName("MED_REC_DISCOUNT");
}catch(Exception ex){
}



int iCommand = FRMQueryString.requestCommand(request);

int start = FRMQueryString.requestInt(request, "start");

int prevCommand = FRMQueryString.requestInt(request, "prev_command");

long oidMedicalRecord = FRMQueryString.requestLong(request, "hidden_medical_record_id");



SrcMedicalRecord srcMedicalRecord = new SrcMedicalRecord();

FrmSrcMedicalRecord frmSrcMedicalRecord = new FrmSrcMedicalRecord(request, srcMedicalRecord);

frmSrcMedicalRecord.requestEntityObject(srcMedicalRecord);

srcMedicalRecord = frmSrcMedicalRecord.getEntityObject();



if(iCommand==Command.LIST){

	session.putValue("SESS_MEDICAL_RECORD", srcMedicalRecord);

}

else{

	if(session.getValue("SESS_MEDICAL_RECORD")!=null){

		srcMedicalRecord = (SrcMedicalRecord)session.getValue("SESS_MEDICAL_RECORD");

	}

}



//out.println(srcMedicalRecord.getEmployeeId()+", "+srcMedicalRecord.getStartDate()+", "+srcMedicalRecord.getEndDate());





/*variable declaration*/

int recordToGet = 8;

String msgString = "";

int iErrCode = FRMMessage.NONE;

String whereClause = "";

String orderClause = "";



CtrlMedicalRecord ctrlMedicalRecord = new CtrlMedicalRecord(request);

ControlLine ctrLine = new ControlLine();

Vector listMedicalRecord = new Vector(1,1);



/*switch statement */

iErrCode = ctrlMedicalRecord.action(iCommand , oidMedicalRecord);

/* end switch*/

FrmMedicalRecord frmMedicalRecord = ctrlMedicalRecord.getForm();



/*count list All MedicalRecord*/

int vectSize = 0;

//if(iCommand!=Command.LIST){

//	vectSize = PstMedicalRecord.getCount(whereClause);

//}else{

	vectSize = SessMedicalRecord.countMedicalRecord(srcMedicalRecord);

	//System.out.println("Masuk : "+vectSize);

//}

//System.out.println("vectSize : "+vectSize);



/*switch list MedicalRecord*/

if((iCommand == Command.FIRST || iCommand == Command.PREV )||

  (iCommand == Command.NEXT || iCommand == Command.LAST)){

		start = ctrlMedicalRecord.actionList(iCommand, start, vectSize, recordToGet);

 } 

/* end switch list*/



MedicalRecord medicalRecord = ctrlMedicalRecord.getMedicalRecord();

msgString =  ctrlMedicalRecord.getMessage();



/* get record to display */

//if(iCommand!=Command.LIST){

//	listMedicalRecord = PstMedicalRecord.list(start,recordToGet,whereClause,orderClause);

//}else{

	listMedicalRecord = SessMedicalRecord.searchMedicalRecord(srcMedicalRecord,start,recordToGet);

	//out.println(srcMedicalRecord.getStartDate());

	//out.println(srcMedicalRecord.getEndDate());

	

//}



/*handle condition if size of record to display = 0 and start > 0 	after delete*/

if (listMedicalRecord.size() < 1 && start > 0)

{

	 if (vectSize - recordToGet > recordToGet)

			start = start - recordToGet;   //go to Command.PREV

	 else{

		 start = 0 ;

		 iCommand = Command.FIRST;

		 prevCommand = Command.FIRST; //go to Command.FIRST

	 }

	 listMedicalRecord = PstMedicalRecord.list(start,recordToGet, whereClause , orderClause);

}

Vector vectFamilyMember = PstFamilyMember.listAll();

Vector vectDiseaseType = PstDiseaseType.listAll();

Vector vectMedicalType = PstMedicalType.listAll();
Vector vectMedicalCase = PstMedicalCase.listAll();



//double budgetUsed = PstMedicalRecord.getTotalMedicalUsed(medicalRecord.getEmployeeId(), medicalRecord.getMedicalTypeId());



Employee employee = new Employee();

FamilyMember falMember = new FamilyMember();

try{

	employee = PstEmployee.fetchExc(medicalRecord.getEmployeeId());

	falMember = PstFamilyMember.fetchExc(medicalRecord.getFamilyMemberId());

}catch(Exception exc){

}

%>

<html>

<!-- #BeginTemplate "/Templates/main.dwt" --> 

<head>

<!-- #BeginEditable "doctitle" --> 

<title>Medical Record</title>

<script language="JavaScript">





function cmdAdd(){

	document.frmmedicalrecord.hidden_medical_record_id.value="0";

	document.frmmedicalrecord.command.value="<%=Command.ADD%>";

	document.frmmedicalrecord.prev_command.value="<%=prevCommand%>";

	document.frmmedicalrecord.action="medicalrecord.jsp";

	document.frmmedicalrecord.submit();

}



function cmdAsk(oidMedicalRecord){

	document.frmmedicalrecord.hidden_medical_record_id.value=oidMedicalRecord;

	document.frmmedicalrecord.command.value="<%=Command.ASK%>";

	document.frmmedicalrecord.prev_command.value="<%=prevCommand%>";

	document.frmmedicalrecord.action="medicalrecord.jsp";

	document.frmmedicalrecord.submit();

}



function cmdConfirmDelete(oidMedicalRecord){

	document.frmmedicalrecord.hidden_medical_record_id.value=oidMedicalRecord;

	document.frmmedicalrecord.command.value="<%=Command.DELETE%>";

	document.frmmedicalrecord.prev_command.value="<%=prevCommand%>";

	document.frmmedicalrecord.action="medicalrecord.jsp";

	document.frmmedicalrecord.submit();

}



function cmdSave(){

	document.frmmedicalrecord.command.value="<%=Command.SAVE%>";

	document.frmmedicalrecord.prev_command.value="<%=prevCommand%>";

	document.frmmedicalrecord.action="medicalrecord.jsp";

	document.frmmedicalrecord.submit();

}



function cmdEdit(oidMedicalRecord){

	document.frmmedicalrecord.hidden_medical_record_id.value=oidMedicalRecord;

	document.frmmedicalrecord.command.value="<%=Command.EDIT%>";

	document.frmmedicalrecord.prev_command.value="<%=prevCommand%>";

	document.frmmedicalrecord.action="medicalrecord.jsp";

	document.frmmedicalrecord.submit();

}



function cmdCancel(oidMedicalRecord){

	document.frmmedicalrecord.hidden_medical_record_id.value=oidMedicalRecord;

	document.frmmedicalrecord.command.value="<%=Command.EDIT%>";

	document.frmmedicalrecord.prev_command.value="<%=prevCommand%>";

	document.frmmedicalrecord.action="medicalrecord.jsp";

	document.frmmedicalrecord.submit();

}



function cmdBack(){

	document.frmmedicalrecord.command.value="<%=Command.BACK%>";

	document.frmmedicalrecord.action="medicalrecord.jsp";

	document.frmmedicalrecord.submit();

}



function cmdListFirst(){

	document.frmmedicalrecord.command.value="<%=Command.FIRST%>";

	document.frmmedicalrecord.prev_command.value="<%=Command.FIRST%>";

	document.frmmedicalrecord.action="medicalrecord.jsp";

	document.frmmedicalrecord.submit();

}



function cmdListPrev(){

	document.frmmedicalrecord.command.value="<%=Command.PREV%>";

	document.frmmedicalrecord.prev_command.value="<%=Command.PREV%>";

	document.frmmedicalrecord.action="medicalrecord.jsp";

	document.frmmedicalrecord.submit();

}



function cmdListNext(){

	document.frmmedicalrecord.command.value="<%=Command.NEXT%>";

	document.frmmedicalrecord.prev_command.value="<%=Command.NEXT%>";

	document.frmmedicalrecord.action="medicalrecord.jsp";

	document.frmmedicalrecord.submit();

}



function cmdBackSearch(){

	document.frmmedicalrecord.command.value="<%=Command.NONE%>";

	document.frmmedicalrecord.action="scrmedicalrecord.jsp";

	document.frmmedicalrecord.submit();

}



function cmdListLast(){

	document.frmmedicalrecord.command.value="<%=Command.LAST%>";

	document.frmmedicalrecord.prev_command.value="<%=Command.LAST%>";

	document.frmmedicalrecord.action="medicalrecord.jsp";

	document.frmmedicalrecord.submit();

}



function cmdSearchEmp(){

     window.open("empsearch.jsp", null, "height=300,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");

}



function cntDiscount(){
	var qty = document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_CASE_QUANTITY]%>.value;

	var amount = document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_AMOUNT]%>.value;

	var disco = document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_PERCENT]%>.value;

	

	if(isNaN(qty) || isNaN(amount) || isNaN(disco)){

		document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_TOTAL]%>.value="0";

		document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_RP]%>.value="0";				

	}

	else{

		if(parseFloat(amount)==0){

			document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_TOTAL]%>.value="0";

			document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_RP]%>.value="0";					

		}

		else{

			if(parseFloat(disco)==0){
                                
				document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_TOTAL]%>.value=(parseFloat(amount)*parseFloat(qty));

				document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_RP]%>.value="0";					

			}else{

				document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_TOTAL]%>.value=(parseFloat(amount) *parseFloat(qty) )- ((parseFloat(disco)*parseFloat(amount))/100);

				document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_RP]%>.value=(parseFloat(disco)*parseFloat(amount))/100;						

			}

		}

	}

	

	/*document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_TOTAL] %>.value=amount/1;

	

	if(document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_TOTAL] %>.value=="NaN"){

		alert("Check Amount");

		document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_AMOUNT] %>.value="";

		document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_TOTAL] %>.value="";

		document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_AMOUNT] %>.focus();

	}	

	var total = document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_AMOUNT] %>.value;	

	var disPercent = document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_PERCENT] %>.value;	

	if(disPercent != 0){

		var disRp = disPercent/100 * total;

		disRp = ""+disRp;

		var token = disRp.indexOf(".");

		if(token > 0)

			token = token + 3;

		else

			token = disRp.length;

		disRp = disRp.substring(0,token);			

		document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_RP] %>.value=disRp;

		if(document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_RP] %>.value=="NaN"){

			alert("Check Discount in Percent");

			document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_PERCENT] %>.value="";

			document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_RP] %>.value="";

			document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_PERCENT] %>.focus();		

		}else{

			document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_TOTAL] %>.value=parseFloat(total)- document.frmmedicalrecord.<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_RP] %>.value;

		}

	}*/

}



//-------------- script form image -------------------



function cmdDelPict(oidMedicalRecord){

	document.frmimage.hidden_medical_record_id.value=oidMedicalRecord;

	document.frmimage.command.value="<%=Command.POST%>";

	document.frmimage.action="medicalrecord.jsp";

	document.frmimage.submit();

}
//update by satrya 2012-12-20
function cmdAddToDiSeaseType(){
    var linkPage = "<%=approot%>/clinic/disease/diseasetype.jsp"; 
                //window.open(linkPage,"Absence Edit","height=600,width=800,status=yes,toolbar=yes,menubar=yes,location=yes");  			
                var newWin = window.open(linkPage,"diseasetype","height=600,width=800,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=no");  			
                newWin.focus();
    
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

        //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';

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

        //document.all.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";

    }

</SCRIPT>

<!-- #EndEditable --> 

</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

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

                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 

                  Clinic &gt; Medical &gt; Medical record<!-- #EndEditable --> 

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

                                    <form name="frmmedicalrecord" method ="post" action="">

                                      <input type="hidden" name="command" value="<%=iCommand%>">

                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">

                                      <input type="hidden" name="start" value="<%=start%>">

                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">

                                      <input type="hidden" name="hidden_medical_record_id" value="<%=oidMedicalRecord%>">
                                      <input type="hidden" name="EMP_LEVEL_ID" value="<%=(employee!=null? employee.getLevelId():0)%>">

                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">

                                        <tr align="left" valign="top"> 

                                          <td height="8"  colspan="3"> 

                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">

                                              <%

												try{

												%>

                                              <tr align="left" valign="top"> 

                                                <td height="22" valign="middle" colspan="3"> 

                                                  <%= drawList(listMedicalRecord,oidMedicalRecord, strMedRecDisc)%> </td>

                                              </tr>

                                              <% 

											  }catch(Exception exc){ 

											  }%>

                                              <tr align="left" valign="top"> 

                                                <td height="8" align="left" colspan="3" class="command" valign="top"> 

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

                                                  <% ctrLine.setLocationImg(approot+"/images/");

												ctrLine.initDefault();

												 %>

                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span> </td>

                                              </tr>

                                              <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmMedicalRecord.errorSize()<1)){%>

                                              <tr align="left" valign="top"> 

                                                <td height="22" colspan="3"> 

                                                  <table width="43%" border="0" cellspacing="1" cellpadding="1">

                                                    <tr> 

                                                      <td width="6%"><img src="../../images/BtnNew.jpg" width="24" height="24"></td>

                                                      <td width="38%"><b><a href="javascript:cmdAdd()" class="buttonlink">Add 

                                                        New Medical Record</a></b></td>

                                                      <td width="6%"><a href="javascript:cmdBackSearch()"><img src="../../images/BtnBack.jpg" width="24" height="24" border="0"></a></td>

                                                      <td width="50%"><b><a href="javascript:cmdBackSearch()">Back 

                                                        To Search Medical Record</a></b></td>

                                                    </tr>

                                                  </table>

                                                </td>

                                              </tr>

                                              <%}%>

                                            </table>

                                          </td>

                                        </tr>

                                        <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmMedicalRecord.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>										

                                        <tr align="left" valign="top" > 

                                          <td colspan="3" class="command"> 

                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">

                                              <tr> 

                                                <td width="11%">&nbsp;</td>

                                                <td width="2%">&nbsp;</td>

                                                <td width="87%">&nbsp;</td>

                                              </tr>

                                              <tr> 

                                                <td width="11%">Employee Name</td>

                                                <td width="2%">:</td>

                                                <td width="87%"> 

                                                  <input type="text" name="EMP_ID" value="<%=employee.getFullName()%>" size="40" disabled class="command">

                                                  <input type="hidden" name="<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=medicalRecord.getEmployeeId()%>">

                                                  <a href="javascript:cmdSearchEmp()" class="buttonlink"><b> 

                                                  Search Employee</b></a></td>

                                              </tr>

                                              <tr> 

                                                <td width="11%"><%=dictionaryD.getWord(I_Dictionary.FAMILY_MEMBER) %></td>

                                                <td width="2%">:</td>

                                                <td width="87%"> 

                                                  <input type="text" name="FAL_MEMBER" value="<%=falMember.getFullName()%>" size="40" disabled>

                                                  <input type="hidden" name="<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_FAMILY_MEMBER_ID]%>" value="<%=medicalRecord.getFamilyMemberId()%>">

                                                </td>

                                              </tr>

                                              <tr> 

                                                <%

												Vector diseasetypeid_value = new Vector(1,1);

												Vector diseasetypeid_key = new Vector(1,1);

												if(vectDiseaseType!=null && vectDiseaseType.size()>0){

													for(int i=0; i<vectDiseaseType.size(); i++){

														DiseaseType diseaseType = (DiseaseType)vectDiseaseType.get(i);

														diseasetypeid_value.add(""+diseaseType.getOID());

														diseasetypeid_key.add(diseaseType.getDiseaseType());

													}

												}

											    %>

                                                <td width="11%">Disease Type</td>

                                                <td width="2%">:</td>

                                                <td width="87%"> <%=ControlCombo.draw(FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISEASE_TYPE_ID],null, ""+medicalRecord.getDiseaseTypeId(), diseasetypeid_value , diseasetypeid_key, "formElemen", "")%> 
                                                    <a href="javascript:cmdAddToDiSeaseType()" class="command">Add New Disease Type</a> </td>
												
                                              </tr>

                                              <tr> 

                                                <%

												Vector medicaltypeid_value = new Vector(1,1);

												Vector medicaltypeid_key = new Vector(1,1);

												if(vectMedicalType!=null && vectMedicalType.size()>0){

													for(int i=0; i<vectMedicalType.size(); i++){

														MedicalType medicalType = (MedicalType)vectMedicalType.get(i);

														medicaltypeid_value.add(""+medicalType.getOID());

														medicaltypeid_key.add(medicalType.getTypeName()); 

													}

												}													

												%>

                                                <td width="11%">Medical Type</td>

                                                <td width="2%">:</td>

                                                <td width="87%"> 
												<table>
													<tr>
														<td><%=ControlCombo.draw(FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_MEDICAL_TYPE_ID],null, ""+medicalRecord.getMedicalTypeId(), medicaltypeid_value , medicaltypeid_key, "formElemen", "")%></td>
														<td><p title="Please insert data to Clinic > Medical > Medical Level;   Clinic >Medical > Medical Case;   Clinic > Medical > Medical Budget ;   Clinic > Medical > Group ;   Clinic > Medical > Type;">
															<img name="Image300" border="0" src="<%=approot%>/images/Info-icon.png" width="20" height="15" alt="Info"></p></td>
													</tr>
												</table>
												</td>
                                                
                                              </tr>


                                              <tr> 

                                                <%
                                                    Vector medCase_value = new Vector(1,1);
                                                    Vector medCase_key = new Vector(1,1);
                                                    medCase_value.add(""+0);
                                                    medCase_key.add("- Please Select -");
                                                    

                                                    if(vectMedicalCase!=null && vectMedicalCase.size()>0){

                                                            for(int i=0; i<vectMedicalCase.size(); i++){

                                                                    MedicalCase medCase = (MedicalCase)vectMedicalCase.get(i);

                                                                    medCase_value.add(""+medCase.getOID());

                                                                    String reqMedCase =""+medCase.getCaseName();
                                                                     if(medCase.getMaxUse()>0 || medCase.getMinTakenBy()>0){
                                                                                      reqMedCase=reqMedCase+"(";
                                                                                      if(medCase.getMaxUse()>0 && medCase.getMaxUsePeriod()>0){
                                                                                          reqMedCase=reqMedCase+"Max "+medCase.getMaxUse()+" "+MedicalCase.PeriodTitle[medCase.getMaxUsePeriod()];
                                                                                      }
                                                                                      if(medCase.getMinTakenBy()>0 && medCase.getMinTakenByPeriod()>0){
                                                                                          reqMedCase=reqMedCase+" & Min Taken "+medCase.getMinTakenBy()+" "+MedicalCase.PeriodTitle[medCase.getMinTakenByPeriod()];
                                                                                      }
                                                                                        
                                                                                      reqMedCase=reqMedCase+")";
                                                                                    }                                                                                     
                                                                                
                                                                    medCase_key.add(reqMedCase); 

                                                            }

                                                    }													

                                                    %>

                                                <td width="11%">Case/Treatment</td>

                                                <td width="2%">:</td>

                                                <td width="87%"> <%=ControlCombo.draw(FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_MEDICAL_CASE_ID],null, ""+medicalRecord.getMedicalCaseId(), medCase_value , medCase_key, "formElemen", "")%> </td>

                                              </tr>
                                              <tr> 

                                                <td width="11%">Record Date</td>

                                                <td width="2%">:</td>

                                                <td width="87%"> <%=ControlDate.drawDateWithStyle(FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_RECORD_DATE] , (medicalRecord.getRecordDate()==null) ? new Date() : medicalRecord.getRecordDate(), 1,-5, "formElemen", "")%> </td>

                                              </tr>
                                              <tr> 

                                                <td width="11%">Case Quantity</td>

                                                <td width="2%">:</td>

                                                <td width="87%"> 

                                                  <input type="text" name="<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_CASE_QUANTITY]%>" value="<%=medicalRecord.getCaseQuantity()%>" onChange="javascript:cntDiscount()">

                                                  * <%=frmMedicalRecord.getErrorMsg(FrmMedicalRecord.FRM_FIELD_CASE_QUANTITY)%> &nbsp; </td>

                                              </tr>

                                              <tr> 
 
                                                <td width="11%">Amount</td>

                                                <td width="2%">:</td>

                                                <td width="87%"> 

                                                  <input type="text" name="<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_AMOUNT]%>" value="<%=medicalRecord.getAmount()%>" onChange="javascript:cntDiscount()">

                                                  * <%=frmMedicalRecord.getErrorMsg(FrmMedicalRecord.FRM_FIELD_AMOUNT)%> &nbsp; </td>

                                              </tr>
                                              <% if((strMedRecDisc!=null ) && (strMedRecDisc.compareTo("1")==0)){
                                              %>
                                              <tr> 

                                                <td width="11%">Discount</td>

                                                <td width="2%">:</td>

                                                <td width="87%"> 

                                                  <input type="text" name="<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_PERCENT]%>" value="<%=medicalRecord.getDiscountInPercent()%>" onChange="javascript:cntDiscount()" size="5">

                                                  in % 

                                                  <input type="text" name="<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_RP]%>" value="<%=Formater.formatNumber(medicalRecord.getDiscountInRp(),"#,###.#")%>" >

                                                  in Rp</td>

                                              </tr> <%} else{%>
                                                  <input type="hidden" name="<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_PERCENT]%>" value="<%=medicalRecord.getDiscountInPercent()%>">                                                  
                                                  <input type="hidden" name="<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_DISCOUNT_IN_RP]%>" value="<%=Formater.formatNumber(medicalRecord.getDiscountInRp(),"#,###.#")%>" >
                                              <%}%>
                                              

                                              <tr> 

                                                <td width="11%">Total</td>

                                                <td width="2%">:</td>

                                                <td width="87%"> 

                                                  <input type="text" readonly name="<%=FrmMedicalRecord.fieldNames[FrmMedicalRecord.FRM_FIELD_TOTAL]%>" value="<%=Formater.formatNumber(medicalRecord.getTotal(),"#,###.#")%>" >

                                                </td>

                                              </tr>

                                              <tr> 

                                                <td width="11%">&nbsp;</td>

                                                <td width="2%">&nbsp;</td>

                                                <td width="87%">&nbsp;</td>

                                              </tr>

                                              <tr> 

                                                <td colspan="3"> 

                                                  <%

											ctrLine.setLocationImg(approot+"/images/");

											ctrLine.initDefault();

											ctrLine.setTableWidth("80%");

											String scomDel = "javascript:cmdAsk('"+oidMedicalRecord+"')";

											String sconDelCom = "javascript:cmdConfirmDelete('"+oidMedicalRecord+"')";

											String scancel = "javascript:cmdEdit('"+oidMedicalRecord+"')";

											ctrLine.setCommandStyle("buttonlink");

											ctrLine.setConfirmDelCaption("Delete Medical Record");

											ctrLine.setDeleteCaption("Delete Medical Record");											

											ctrLine.setAddCaption("Add New Medical Record");											

											ctrLine.setSaveCaption("Save Medical Record");											

											ctrLine.setBackCaption("Back to List Medical Record");																																	



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

                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%></td>

                                              </tr>

                                            </table>

                                          </td>

                                        </tr>

										<%}%>

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

