<%-- 
    Document   : outSourceEvaluationProvider
    Created on : Sep 30, 2011, 3:56:51 PM
    Author     : Wiweka
--%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourceEvaluation"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourceEvaluation"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourceEvaluationProvider"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourceEvaluationProvider"%>
<%@page import="com.dimata.harisma.form.outsource.FrmOutSourceEvaluationProvider"%>
<%@page import="com.dimata.harisma.form.outsource.CtrlOutSourceEvaluationProvider"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%
            /*
             * Page Name  		:  outsource_evaluation_provider.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: Ari_20110930
             * @version  		: -
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
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_OUTSOURCE, AppObjInfo.G2_OUTSOURCE_DATA_ENTRY, AppObjInfo.OBJ_OUTSOURCE_FORM_EVALUASI  );%>

<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_GROUP_RANK); %>
<%@ include file = "../main/checkuser.jsp" %>
<%!

	public String drawList(int iCommand,FrmOutSourceEvaluationProvider frmObject, OutSourceEvaluationProvider outSourceEvaluationProviderObj, Vector objectClass ,  long docOutSourceEvaluationProvider, long oidOutSourceEvaluation)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
                ctrlist.addHeader("no","10%");
                ctrlist.addHeader("Posisi","15%");
		ctrlist.addHeader("Penyedia","20%");
		ctrlist.addHeader("Nilai","20%");
                ctrlist.addHeader("Alasan","20%");
                ctrlist.addHeader("Usulan","20%");
                ctrlist.addHeader("Kariawan di Akhir Bulan","20%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
	

                Vector div_value = new Vector(1, 1);
                Vector div_key = new Vector(1, 1);
                Vector listdiv = PstDivision.list(0, 0, "", "DIVISION");
                div_value.add(""+0);
                div_key.add("select");
                for (int i = 0; i < listdiv.size(); i++) {
                    Division div = (Division) listdiv.get(i);
                    div_key.add(div.getDivision());
                    div_value.add(String.valueOf(div.getOID()));
                }
                
                Vector position_value = new Vector(1, 1);
                Vector position_key = new Vector(1, 1);
                Vector listposition = PstPosition.list(0, 0, "", "");
                position_value.add(""+0);
                position_key.add("select");
                for (int i = 0; i < listposition.size(); i++) {
                    Position posisiton = (Position ) listposition.get(i);
                    position_key.add(posisiton.getPosition());
                    position_value.add(String.valueOf(posisiton.getOID()));
                }
 
            
                Vector provider_value = new Vector(1, 1);
                Vector provider_key = new Vector(1, 1);
                Vector listprovider = PstContactList.list(0, 0, "", "");
                provider_value.add(""+0);
                provider_key.add("select");
                for (int i = 0; i < listprovider.size(); i++) {
                    ContactList contactList = (ContactList) listprovider.get(i);
                    provider_key.add(contactList.getCompName());
                    provider_value.add(String.valueOf(contactList.getOID()));
                }
                
                
                 Vector val_nilai = new Vector(1,1);
             Vector key_nilai = new Vector(1,1);
             val_nilai.add(""+0) ;
             key_nilai.add("Select");
             val_nilai.add(""+1) ;
             key_nilai.add("Buruk");
             val_nilai.add(""+2) ;
             key_nilai.add("Cukup Baik");
             val_nilai.add(""+3) ;
             key_nilai.add("Baik");
             val_nilai.add(""+4) ;
             key_nilai.add("Sangat Baik");

             Hashtable hashstatus = new Hashtable();
             hashstatus.put(""+0, "Select");
             hashstatus.put(""+1, "Buruk");
             hashstatus.put(""+2, "Cukup Baik");
             hashstatus.put(""+3, "Baik");
             hashstatus.put(""+4, "Sangat Baik");
                
                
                Hashtable Hlistlistprovider = PstContactList.hashlistTblPeriodName(0, 0, "", "");
                Hashtable HlistlistDivision = PstDivision.listMapDivisionName(0, 0, "", "");
                Hashtable HlistlistPosition = PstPosition.listMapPositionName(0, 0, "", "");
                
                long tempOidPosition = 0;
                int tempNumberOfPerson = 0;
		for (int i = 0; i < objectClass.size(); i++) {
			 OutSourceEvaluationProvider outSourceEvaluationProvider = (OutSourceEvaluationProvider)objectClass.get(i);
                         tempNumberOfPerson = tempNumberOfPerson + outSourceEvaluationProvider.getNumberOfPerson();  
                         //if (outSourceEvaluationProvider.getPositionId() != tempOidPosition){
                          //   tempOidPosition = outSourceEvaluationProvider.getPositionId();
                          //   if (i != 0){
                          //      rowx = new Vector(); 
                          //      rowx.add("");
                          //      rowx.add("");
                          //      rowx.add("");
                          //      rowx.add("");
                          //      rowx.add("");
                          //      rowx.add(""+ tempOidPosition);
                          //      tempNumberOfPerson = 0 ;
                          //     lstData.add(rowx);
                          //   }
                             
                         //}
                             
                         
			 rowx = new Vector();
                         //cek apakah ada doc master template
                      
                         rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(outSourceEvaluationProvider.getOID())+"')\">"+(i+1)+"</a>");
				        
			 if((iCommand == Command.EDIT || iCommand == Command.ASK) && (outSourceEvaluationProvider.getOID() == docOutSourceEvaluationProvider)){				
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_POSITION_ID], "formElemen", null, String.valueOf(outSourceEvaluationProvider.getPositionId()), position_value, position_key, ""));
                                //rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_PERIOD_ID], "formElemen", null, String.valueOf(outSourceEvaluationProvider.getPeriodId()), period_value, period_key, ""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_PROVIDER_ID], "formElemen", null, String.valueOf(outSourceEvaluationProvider.getProviderId()), provider_value, provider_key, ""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_EVAL_POINT], "formElemen", null, String.valueOf(outSourceEvaluationProvider.getEvalPoint()), val_nilai, key_nilai, ""));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_JUSTIFICATION] +"\" value=\""+outSourceEvaluationProvider.getJustification()+"\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_SUGGESTION] +"\" value=\""+outSourceEvaluationProvider.getSuggestion()+"\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_NUMBER_OF_PERSON] +"\" value=\""+outSourceEvaluationProvider.getNumberOfPerson()+"\" class=\"elemenForm\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_OUT_SOURCE_EVAL_ID] +"\" value=\""+oidOutSourceEvaluation+"\" class=\"elemenForm\">");
				
                                
                         }else{
                                rowx.add(""+HlistlistPosition.get(outSourceEvaluationProvider.getPositionId()));
                                //rowx.add("");
                                rowx.add(""+Hlistlistprovider.get(outSourceEvaluationProvider.getProviderId()));
                                rowx.add(""+ hashstatus.get(""+outSourceEvaluationProvider.getEvalPoint()));
                                rowx.add(""+ outSourceEvaluationProvider.getJustification());
                                rowx.add(""+ outSourceEvaluationProvider.getSuggestion());
                                rowx.add(""+ outSourceEvaluationProvider.getNumberOfPerson());
                                
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
                                rowx.add("-");
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_POSITION_ID], "formElemen", null, String.valueOf(outSourceEvaluationProviderObj.getPositionId()), position_value, position_key, ""));
                                //rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_PERIOD_ID], "formElemen", null, String.valueOf(outSourceEvaluationProvider.getPeriodId()), period_value, period_key, ""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_PROVIDER_ID], "formElemen", null, String.valueOf(outSourceEvaluationProviderObj.getProviderId()), provider_value, provider_key, ""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_EVAL_POINT], "formElemen", null, String.valueOf(outSourceEvaluationProviderObj.getEvalPoint()), val_nilai, key_nilai, ""));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_JUSTIFICATION] +"\" value=\""+outSourceEvaluationProviderObj.getJustification()+"\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_SUGGESTION] +"\" value=\""+outSourceEvaluationProviderObj.getSuggestion()+"\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_NUMBER_OF_PERSON] +"\" value=\""+outSourceEvaluationProviderObj.getNumberOfPerson()+"\" class=\"elemenForm\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluationProvider.FRM_FIELD_OUT_SOURCE_EVAL_ID] +"\" value=\""+oidOutSourceEvaluation+"\" class=\"elemenForm\">");
				                               
		}

		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidOutSourceEvaluationProvider = FRMQueryString.requestLong(request, "hidden_outSourceEvaluationProvider_id");
            long oidOutSourceEvaluation = FRMQueryString.requestLong(request, "hidden_outSourceEvaluation_id");

            OutSourceEvaluation outSourceEvaluation = new OutSourceEvaluation();
            if (oidOutSourceEvaluation > 0 ){
                try {
                    outSourceEvaluation = PstOutSourceEvaluation.fetchExc(oidOutSourceEvaluation);
                } catch (Exception e ){
                }
            }

            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = ""+PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_OUT_SOURCE_EVAL_ID] + " = " +oidOutSourceEvaluation;
            String orderClause = " POSITION_ID ";

            CtrlOutSourceEvaluationProvider ctrlOutSourceEvaluationProvider = new CtrlOutSourceEvaluationProvider(request);
            ControlLine ctrLine = new ControlLine();
            Vector listOutSourceEvaluationProvider = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlOutSourceEvaluationProvider.action(iCommand, oidOutSourceEvaluationProvider);
            /* end switch*/
            FrmOutSourceEvaluationProvider frmOutSourceEvaluationProvider = ctrlOutSourceEvaluationProvider.getForm();

            /*count list All Position*/
            int vectSize = PstOutSourceEvaluationProvider.getCount(whereClause);

            OutSourceEvaluationProvider outSourceEvaluationProvider = ctrlOutSourceEvaluationProvider.getOutSourceEvaluationProvider();
            msgString = ctrlOutSourceEvaluationProvider.getMessage();

            /*switch list OutSourceEvaluationProvider*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                //start = PstOutSourceEvaluationProvider.findLimitStart(outSourceEvaluationProvider.getOID(),recordToGet, whereClause);
                oidOutSourceEvaluationProvider = outSourceEvaluationProvider.getOID();
            }

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlOutSourceEvaluationProvider.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listOutSourceEvaluationProvider = PstOutSourceEvaluationProvider.list(start, recordToGet, whereClause, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listOutSourceEvaluationProvider.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listOutSourceEvaluationProvider = PstOutSourceEvaluationProvider.list(start, recordToGet, whereClause, orderClause);
            }

                

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>DIMATA HARISMA - Outsource Evaluation Provider</title>
        <script language="JavaScript">

function setEmployee(){
       window.open("<%=approot%>/employee/search/search.jsp?formName=frm_leave_application&empPathId=EMPID", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");           
    }


            function cmdAdd(){
                document.frmoutSourceEvaluationProvider.hidden_outSourceEvaluationProvider_id.value="0";
                document.frmoutSourceEvaluationProvider.command.value="<%=Command.ADD%>";
                document.frmoutSourceEvaluationProvider.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluationProvider.action="outsource_evaluation_provider.jsp";
                document.frmoutSourceEvaluationProvider.submit();
            }

            function cmdAsk(oidOutSourceEvaluationProvider){
                document.frmoutSourceEvaluationProvider.hidden_outSourceEvaluationProvider_id.value=oidOutSourceEvaluationProvider;
                document.frmoutSourceEvaluationProvider.command.value="<%=Command.ASK%>";
                document.frmoutSourceEvaluationProvider.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluationProvider.action="outsource_evaluation_provider.jsp";
                document.frmoutSourceEvaluationProvider.submit();
            }

            function cmdConfirmDelete(oidOutSourceEvaluationProvider){
                document.frmoutSourceEvaluationProvider.hidden_outSourceEvaluationProvider_id.value=oidOutSourceEvaluationProvider;
                document.frmoutSourceEvaluationProvider.command.value="<%=Command.DELETE%>";
                document.frmoutSourceEvaluationProvider.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluationProvider.action="outsource_evaluation_provider.jsp";
                document.frmoutSourceEvaluationProvider.submit();
            }
            function cmdSave(){
                document.frmoutSourceEvaluationProvider.command.value="<%=Command.SAVE%>";
                document.frmoutSourceEvaluationProvider.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluationProvider.action="outsource_evaluation_provider.jsp";
                document.frmoutSourceEvaluationProvider.submit();
            }

           function cmdApprove(hidden_outSourceEvaluation_id){
                    document.frmoutSourceEvaluationProvider.action="evaluationApproval.jsp?hidden_outSourceEvaluation_id="+hidden_outSourceEvaluation_id;
                    document.frmoutSourceEvaluationProvider.submit(); 
            }

            function cmdEdit(oidOutSourceEvaluationProvider){
                document.frmoutSourceEvaluationProvider.hidden_outSourceEvaluationProvider_id.value=oidOutSourceEvaluationProvider;
                document.frmoutSourceEvaluationProvider.command.value="<%=Command.EDIT%>";
                document.frmoutSourceEvaluationProvider.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluationProvider.action="outsource_evaluation_provider.jsp";
                document.frmoutSourceEvaluationProvider.submit();
            }

            function cmdCancel(oidOutSourceEvaluationProvider){
                document.frmoutSourceEvaluationProvider.hidden_outSourceEvaluationProvider_id.value=oidOutSourceEvaluationProvider;
                document.frmoutSourceEvaluationProvider.command.value="<%=Command.EDIT%>";
                document.frmoutSourceEvaluationProvider.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluationProvider.action="outsource_evaluation_provider.jsp";
                document.frmoutSourceEvaluationProvider.submit();
            }

            function cmdBack(){
                document.frmoutSourceEvaluationProvider.command.value="<%=Command.BACK%>";
                document.frmoutSourceEvaluationProvider.action="outsource_evaluation_provider.jsp";
                document.frmoutSourceEvaluationProvider.submit();
            }

            function cmdListFirst(){
                document.frmoutSourceEvaluationProvider.command.value="<%=Command.FIRST%>";
                document.frmoutSourceEvaluationProvider.prev_command.value="<%=Command.FIRST%>";
                document.frmoutSourceEvaluationProvider.action="outsource_evaluation_provider.jsp";
                document.frmoutSourceEvaluationProvider.submit();
            }

            function cmdListPrev(){
                document.frmoutSourceEvaluationProvider.command.value="<%=Command.PREV%>";
                document.frmoutSourceEvaluationProvider.prev_command.value="<%=Command.PREV%>";
                document.frmoutSourceEvaluationProvider.action="outsource_evaluation_provider.jsp";
                document.frmoutSourceEvaluationProvider.submit();
            }

            function cmdListNext(){
                document.frmoutSourceEvaluationProvider.command.value="<%=Command.NEXT%>";
                document.frmoutSourceEvaluationProvider.prev_command.value="<%=Command.NEXT%>";
                document.frmoutSourceEvaluationProvider.action="outsource_evaluation_provider.jsp";
                document.frmoutSourceEvaluationProvider.submit();
            }

            function cmdListLast(){
                document.frmoutSourceEvaluationProvider.command.value="<%=Command.LAST%>";
                document.frmoutSourceEvaluationProvider.prev_command.value="<%=Command.LAST%>";
                document.frmoutSourceEvaluationProvider.action="outsource_evaluation_provider.jsp";
                document.frmoutSourceEvaluationProvider.submit();
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
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
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

        </SCRIPT>
        <!-- #EndEditable -->
    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%//@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
                    <!-- #BeginEditable "header" -->
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable -->
                </td>
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
                                        <td height="20">
                                            <font color="#FF6600" face="Arial"><strong>
                                                    <!-- #BeginEditable "contenttitle" -->
                                                    Out Source Evaluation<!-- #EndEditable -->
                                                </strong></font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                            <tr>
                                                                <td valign="top">
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frmoutSourceEvaluationProvider" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_outSourceEvaluationProvider_id" value="<%=oidOutSourceEvaluationProvider%>">
                                                                                    <input type="hidden" name="hidden_outSourceEvaluation_id" value="<%=oidOutSourceEvaluation%>">
                                                                                    
                                                                                   
                                                                                    
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;OutSourceEvaluationProvider List </td>
                                                                                                    </tr>
                                                                                                
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="22" valign="middle" colspan="3">
                                                                                                            <%=drawList(iCommand ,frmOutSourceEvaluationProvider ,outSourceEvaluationProvider,listOutSourceEvaluationProvider, oidOutSourceEvaluationProvider , oidOutSourceEvaluation )%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%

                                                                                                   if (outSourceEvaluation.getStatusDoc() == 1) {%>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="22" valign="middle" colspan="3">
                                                                                                            <a href="javascript:cmdApprove('<%=oidOutSourceEvaluation%>')" class="command">Approved </a> 
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <% }%>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="8" align="left" colspan="3" class="command">
                                                                                                            <span class="command">
                                                                                                                <%
                                                                                                                            int cmd = 0;
                                                                                                                            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                                                                                                                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                                                                                                                cmd = iCommand;
                                                                                                                            } else {
                                                                                                                                if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                                                                                                                    cmd = Command.FIRST;
                                                                                                                                } else {
                                                                                                                                    cmd = prevCommand;
                                                                                                                                }
                                                                                                                            }
                                                                                                                %>
                                                                                                                <% ctrLine.setLocationImg(approot + "/images");
                                                                                                                            ctrLine.initDefault();
                                                                                                                %>
                                                                                                                <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%>
                                                                                                            </span> </td>
                                                                                                    </tr>
                                                                                                    
                                                                                                    
                                                                                                       <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmOutSourceEvaluationProvider.errorSize()<1)){
                                                                                                    if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmOutSourceEvaluationProvider.errorSize() < 1)) {
                                                                                                        if (privAdd) {%>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td>
                                                                                                            <table cellpadding="0" cellspacing="0" border="0">
                                                                                                                <tr>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                                    <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td height="22" valign="middle" colspan="3" width="951">
                                                                                                                    <a href="javascript:cmdAdd()" class="command">Add  New </a> </td>
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%}
                                                                                                      }%>
                                                                                                </table>
                                                                                                <tr align="left" valign="top">
                                                                                            <td height="8" valign="middle" colspan="3">
                                                                                                <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmOutSourceEvaluationProvider.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    
                                                                                                    
                                                                                                    <tr>
                                                                                                        <td colspan="2">
                                                                                                            <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80%");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidOutSourceEvaluationProvider + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidOutSourceEvaluationProvider + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidOutSourceEvaluationProvider + "')";
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setSaveCaption("Save");
                                                                                                                ctrLine.setConfirmDelCaption("Yes Delete");
                                                                                                                ctrLine.setDeleteCaption("Delete");

                                                                                                                if (privDelete) {
                                                                                                                    ctrLine.setConfirmDelCommand(sconDelCom);
                                                                                                                    ctrLine.setDeleteCommand(scomDel);
                                                                                                                    ctrLine.setEditCommand(scancel);
                                                                                                                } else {
                                                                                                                    ctrLine.setConfirmDelCaption("");
                                                                                                                    ctrLine.setDeleteCaption("");
                                                                                                                    ctrLine.setEditCaption("");
                                                                                                                }

                                                                                                                if (privAdd == false && privUpdate == false) {
                                                                                                                    ctrLine.setSaveCaption("");
                                                                                                                }

                                                                                                                if (privAdd == false) {
                                                                                                                    ctrLine.setAddCaption("");
                                                                                                                }

                                                                                                                if (iCommand == Command.ASK) {
                                                                                                                    ctrLine.setDeleteQuestion(msgString);
                                                                                                                }
                                                                                                            %>
                                                                                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                                <%}%>
                                                                                            </td>
                                                                                        </tr>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>&nbsp;
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
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
                //var oBody = document.body;
                //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>

