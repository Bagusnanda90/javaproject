<%-- 
    Document   : Doc Master Action
    Created on : Sep 12, 2015, 3:56:51 PM
    Author     : Priska
--%>
<%@page import="javax.print.DocFlavor.STRING"%>
<%@page import="org.apache.poi.ss.formula.functions.Hlookup"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
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

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<%@ include file = "../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!    public String drawList(String actionNameKey, long docMasterId, Vector ObjectName, int command,long docActionId) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("90%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("NAME", "30%");
        ctrlist.addHeader("OBJECT NAME", "40%");
        ctrlist.addHeader("OBJECT ATTRIBUT", "30%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.reset();
        int index = -1;
        int cekIndex = DocMasterActionClass.getIndexActionValue(actionNameKey);
        String[] actionListParameter = DocMasterActionClass.actionListParameterKey[cekIndex];
             
        String whereClause = PstDocMasterActionParam.fieldNames[PstDocMasterActionParam.FLD_DOC_ACTION_ID]+" = "+docActionId;
        Hashtable listDocActionParam = PstDocMasterActionParam.hList(0, 0, "", "");
        for (int i = 0; i < actionListParameter.length; i++) {
            Vector rowx = new Vector();
            
            DocMasterActionParam docMasterActionParam = new DocMasterActionParam();
            try {
                docMasterActionParam = (DocMasterActionParam) listDocActionParam.get(actionListParameter[i]);
            } catch (Exception e){
                
            }
           
            
           String[] attribut = DocMasterActionClass.actionListParameterAttribut[cekIndex];
            
            //String[] listparameter = actionListParameter[i];
            Vector vListparam = DocMasterActionClass.getVectorfromArray(attribut);
            
            
            if (command == Command.EDIT){
            rowx.add("<input type=\"text\" name=\""+FrmDocMasterAction.fieldNames[FrmDocMasterAction.FRM_FIELD_ACTION_PARAMETER]+i +"\" value=\""+actionListParameter[i]+"\" class=\"elemenForm\">");
            rowx.add(ControlCombo.draw(FrmDocMasterAction.fieldNames[FrmDocMasterAction.FRM_FIELD_OBJECT_NAME]+i, "formElemen", null, ""+(docMasterActionParam != null?docMasterActionParam.getObjectName():""), ObjectName, ObjectName, ""));
            rowx.add(ControlCombo.draw(FrmDocMasterAction.fieldNames[FrmDocMasterAction.FRM_FIELD_OBJECT_ATTRIBUT]+i, "formElemen", null, (docMasterActionParam != null?docMasterActionParam.getObjectAtribut():""), vListparam, vListparam, ""));
                       } else if (command == Command.ADD){
            rowx.add("<input type=\"text\" name=\""+FrmDocMasterAction.fieldNames[FrmDocMasterAction.FRM_FIELD_ACTION_PARAMETER]+i +"\" value=\""+actionListParameter[i]+"\" class=\"elemenForm\">");
            rowx.add(ControlCombo.draw(FrmDocMasterAction.fieldNames[FrmDocMasterAction.FRM_FIELD_OBJECT_NAME]+i, "formElemen", null, "", ObjectName, ObjectName, ""));
            rowx.add(ControlCombo.draw(FrmDocMasterAction.fieldNames[FrmDocMasterAction.FRM_FIELD_OBJECT_ATTRIBUT]+i, "formElemen", null, "", vListparam, vListparam, ""));
              
                       } else {    
            rowx.add(""+actionListParameter[i]);
            rowx.add(""+(docMasterActionParam != null?docMasterActionParam.getObjectName():""));
            rowx.add(""+(docMasterActionParam != null?docMasterActionParam.getObjectAtribut():""));
               
                       }
            
                                 
            lstData.add(rowx);
//            lstLinkData.add(String.valueOf(company.getOID()));
        }
        return ctrlist.draw(index);
    }

%>
<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidDocMasterAction = FRMQueryString.requestLong(request, "hidden_docMasterAction_id");
            
            long DocMasterId = FRMQueryString.requestLong(request, FrmDocMasterAction.fieldNames[FrmDocMasterAction.FRM_FIELD_DOC_MASTER_ID]);
            String actionNameKey = FRMQueryString.requestString(request, FrmDocMasterAction.fieldNames[FrmDocMasterAction.FRM_FIELD_ACTION_NAME] );
            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = PstDocMasterAction.fieldNames[PstDocMasterAction.FLD_DOC_MASTER_ID]+" = "+DocMasterId;
            String orderClause = "";

            CtrlDocMasterAction ctrlDocMasterAction = new CtrlDocMasterAction(request);
            ControlLine ctrLine = new ControlLine();
            Vector listDocMasterAction = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlDocMasterAction.action(iCommand, oidDocMasterAction, actionNameKey);
            /* end switch*/
            FrmDocMasterAction frmDocMasterAction = ctrlDocMasterAction.getForm();


            DocMasterAction docMasterAction = ctrlDocMasterAction.getdDocMasterAction();
            msgString = ctrlDocMasterAction.getMessage();

            /*switch list DocMasterAction*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                //start = PstDocMasterAction.findLimitStart(docMasterAction.getOID(),recordToGet, whereClause);
                oidDocMasterAction = docMasterAction.getOID();
            }

            listDocMasterAction = PstDocMasterAction.list(start, recordToGet, whereClause, orderClause);

            //if (listDocMasterAction.size()>0){
            //    try {
            //        docMasterAction = (DocMasterAction) listDocMasterAction.get(0);
            //    }catch(Exception e){
             //   }
            //}
                

%>



<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Master Data DocMasterAction</title>
        <script language="JavaScript">

           function cmdActionKey(){
                document.frmdocMasterAction.action="doc_master_action.jsp";
                document.frmdocMasterAction.submit();
            }

            function cmdAdd(){
                document.frmdocMasterAction.hidden_docMasterAction_id.value="0";
                document.frmdocMasterAction.command.value="<%=Command.ADD%>";
                document.frmdocMasterAction.prev_command.value="<%=prevCommand%>";
                document.frmdocMasterAction.action="doc_master_action.jsp";
                document.frmdocMasterAction.submit();
            }

            function cmdAsk(oidDocMasterAction){
                document.frmdocMasterAction.hidden_docMasterAction_id.value=oidDocMasterAction;
                document.frmdocMasterAction.command.value="<%=Command.ASK%>";
                document.frmdocMasterAction.prev_command.value="<%=prevCommand%>";
                document.frmdocMasterAction.action="doc_master_action.jsp";
                document.frmdocMasterAction.submit();
            }

            function cmdConfirmDelete(oidDocMasterAction){
                document.frmdocMasterAction.hidden_docMasterAction_id.value=oidDocMasterAction;
                document.frmdocMasterAction.command.value="<%=Command.DELETE%>";
                document.frmdocMasterAction.prev_command.value="<%=prevCommand%>";
                document.frmdocMasterAction.action="doc_master_action.jsp";
                document.frmdocMasterAction.submit();
            }
            function cmdSave(){
                document.frmdocMasterAction.command.value="<%=Command.SAVE%>";
                document.frmdocMasterAction.prev_command.value="<%=prevCommand%>";
                document.frmdocMasterAction.action="doc_master_action.jsp";
                document.frmdocMasterAction.submit();
            }

            function cmdEdit(hidden_docMasterAction_id){
                alert(hidden_docMasterAction_id);
                document.frmdocMasterAction.hidden_docMasterAction_id.value=hidden_docMasterAction_id;
                document.frmdocMasterAction.command.value="<%=Command.EDIT%>";
                document.frmdocMasterAction.prev_command.value="<%=prevCommand%>";
                document.frmdocMasterAction.action="doc_master_action.jsp?hidden_docMasterAction_id="+hidden_docMasterAction_id;
                document.frmdocMasterAction.submit();
            }

            function cmdCancel(oidDocMasterAction){
                document.frmdocMasterAction.hidden_docMasterAction_id.value=oidDocMasterAction;
                document.frmdocMasterAction.command.value="<%=Command.EDIT%>";
                document.frmdocMasterAction.prev_command.value="<%=prevCommand%>";
                document.frmdocMasterAction.action="doc_master_action.jsp";
                document.frmdocMasterAction.submit();
            }

            function cmdBack(){
                document.frmdocMasterAction.command.value="<%=Command.BACK%>";
                document.frmdocMasterAction.action="doc_master_action.jsp";
                document.frmdocMasterAction.submit();
            }

            function cmdListFirst(){
                document.frmdocMasterAction.command.value="<%=Command.FIRST%>";
                document.frmdocMasterAction.prev_command.value="<%=Command.FIRST%>";
                document.frmdocMasterAction.action="doc_master_action.jsp";
                document.frmdocMasterAction.submit();
            }

            function cmdListPrev(){
                document.frmdocMasterAction.command.value="<%=Command.PREV%>";
                document.frmdocMasterAction.prev_command.value="<%=Command.PREV%>";
                document.frmdocMasterAction.action="doc_master_action.jsp";
                document.frmdocMasterAction.submit();
            }

            function cmdListNext(){
                document.frmdocMasterAction.command.value="<%=Command.NEXT%>";
                document.frmdocMasterAction.prev_command.value="<%=Command.NEXT%>";
                document.frmdocMasterAction.action="doc_master_action.jsp";
                document.frmdocMasterAction.submit();
            }

            function cmdListLast(){
                document.frmdocMasterAction.command.value="<%=Command.LAST%>";
                document.frmdocMasterAction.prev_command.value="<%=Command.LAST%>";
                document.frmdocMasterAction.action="doc_master_action.jsp";
                document.frmdocMasterAction.submit();
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
                                                    SET ACTION
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
                                                                                <%
                                                                                 
                                                                                                                        String empDocMasterTemplateText = "";
                                                                                                                        try {
                                                                                                                            empDocMasterTemplateText = PstDocMasterTemplate.getTemplateText(DocMasterId);
                                                                                                                        } catch (Exception e){ 
                                                                                                                        }


                                                                                                                        String tanpaeditor = empDocMasterTemplateText;
                                                                                                                        String subString = "";
                                                                                                                        String stringResidual = empDocMasterTemplateText;
                                                                                                                        Vector vNewString = new Vector();

                                                                                                                        Hashtable empDOcFecthH = new Hashtable();



                                                                                                                        int startPosition = 0 ;
                                                                                                                        int endPosition = 0;                                             
                                                                                                                        do {

                                                                                                                                ObjectDocumentDetail objectDocumentDetail = new ObjectDocumentDetail();
                                                                                                                                startPosition = stringResidual.indexOf("${") + "${".length();
                                                                                                                                endPosition = stringResidual.indexOf("}", startPosition);
                                                                                                                                subString = stringResidual.substring(startPosition, endPosition);


                                                                                                                                //cek substring


                                                                                                                                    String []parts = subString.split("-");
                                                                                                                                    String objectName = "";
                                                                                                                                    String objectType = "";
                                                                                                                                    String objectClass = "";
                                                                                                                                    String objectStatusField = "";
                                                                                                                                    try{
                                                                                                                                    objectName = parts[0]; 
                                                                                                                                    objectType = parts[1];
                                                                                                                                    objectClass = parts[2];
                                                                                                                                    objectStatusField = parts[3];
                                                                                                                                    } catch (Exception e){
                                                                                                                                        System.out.printf("pastikan 4 parameter");
                                                                                                                                    }


                                                                                                                                //cek dulu apakah hanya object name atau tidak
                                                                                                                                if  (!objectName.equals("") && !objectType.equals("") && !objectClass.equals("") && !objectStatusField.equals("")){


                                                                                                                                    //jika list maka akan mencari penutupnya..
                                                                                                                                if  (objectType.equals("LIST") && objectStatusField.equals("START")){
                                                                                                                                  //  String add = "<a href=\"javascript:cmdAddEmp('"+objectName+"','"+oidEmpDoc+"')\">add employee</a></br>";
                                                                                                                                  //  empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                                                                                                  //  tanpaeditor = tanpaeditor.replace("${"+subString+"}", " ");    
                                                                                                                                    int endPnutup = stringResidual.indexOf("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", endPosition);
                                                                                                                                        //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                                                                                                                                        String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                                                                                                                                       //menghapus tutup formula 


                                                                                                                                        //mencari jumlah table table
                                                                                                                                          int startPositionOfTable = 0;
                                                                                                                                          int endPositionOfTable = 0;
                                                                                                                                          String subStringOfTable = "";
                                                                                                                                          String residueOfTextString = textString;
                                                                                                                                          do{
                                                                                                                                              //cari tag table pembuka
                                                                                                                                              startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                                                                                                                                              //int tt = textString.indexOf("&lttable") + ((textString.indexOf("&gt") + 3)-textString.indexOf("&lttable"));
                                                                                                                                              endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);
                                                                                                                                              subStringOfTable = residueOfTextString.substring(startPositionOfTable, endPositionOfTable);//isi table 

                                                                                                                                              //mencari body 
                                                                                                                                              int startPositionOfBody = subStringOfTable.indexOf("<tbody>") + "<tbody>".length();
                                                                                                                                              int endPositionOfBody = subStringOfTable.indexOf("</tbody>", startPositionOfBody);
                                                                                                                                              String subStringOfBody = subStringOfTable.substring(startPositionOfBody, endPositionOfBody);//isi body

                                                                                                                                              //mencari tr pertama pada table
                                                                                                                                              int startPositionOfTr1 = subStringOfBody.indexOf("<tr>") + "<tr>".length();
                                                                                                                                              int endPositionOfTr1 = subStringOfBody.indexOf("</tr>", startPositionOfTr1);
                                                                                                                                              String subStringOfTr1 = subStringOfBody.substring(startPositionOfTr1, endPositionOfTr1);

                                                                                                                                              String subStringOfBody2 = subStringOfBody.substring(endPositionOfTr1, subStringOfBody.length());//isi body setelah dipotong tr pertama
                                                                                                                                              int startPositionOfTr2 = subStringOfBody2.indexOf("<tr>") + "<tr>".length();
                                                                                                                                              int endPositionOfTr2 = subStringOfBody2.indexOf("</tr>", startPositionOfTr2);
                                                                                                                                              String subStringOfTr2 = subStringOfBody2.substring(startPositionOfTr2, endPositionOfTr2);

                                                                                                                                              //disini diisi perulanganya


                                                                                                                                             //baca table dibawahnya

                                                                                                                                             String stringTrReplace = ""; 


                                                                                                                                             stringTrReplace = stringTrReplace+"<tr>"; 

                                                                                                                                              //menghitung jumlah td html
                                                                                                                                              int startPositionOfTd = 0;
                                                                                                                                              int endPositionOfTd = 0;
                                                                                                                                              String subStringOfTd = "";
                                                                                                                                              String residuOfsubStringOfTr2 = subStringOfTr2 ;
                                                                                                                                              int jumlahtd = 0;



                                                                                                                                              do{

                                                                                                                                              stringTrReplace = stringTrReplace+"<td>";  

                                                                                                                                              startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                                                                                                                              endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                                                                                                                              subStringOfTd = residuOfsubStringOfTr2.substring(startPositionOfTd, endPositionOfTd);//isi table 

                                                                                                                                              int startPositionOfFormula = 0;
                                                                                                                                              int endPositionOfFormula = 0;
                                                                                                                                              String subStringOfFormula = "";
                                                                                                                                              String residuOfsubStringOfTd = subStringOfTd;
                                                                                                                                                      do{

                                                                                                                                                        startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                                                                                                                                        endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                                                                                                        subStringOfFormula = residuOfsubStringOfTd.substring(startPositionOfFormula, endPositionOfFormula);//isi table 


                                                                                                                                                        String []partsOfFormula = subStringOfFormula.split("-");
                                                                                                                                                        String objectNameFormula = partsOfFormula[0]; 
                                                                                                                                                        String objectTypeFormula = partsOfFormula[1];
                                                                                                                                                        String objectTableFormula = partsOfFormula[2];
                                                                                                                                                        String objectStatusFormula = partsOfFormula[3];
                                                                                                                                                        String value = "";
                                                                                                                                                        if (objectTableFormula.equals("EMPLOYEE")){
                                                                                                                                                                //value = (String) HashtableEmp.get(objectStatusFormula);
                                                                                                                                                                //if (objectStatusFormula.equals(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME])){
                                                                                                                                                                //    value = (String) employeeFetch.getFullName();
                                                                                                                                                                //} else if (objectStatusFormula.equals(PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS])){
                                                                                                                                                                //    value = (String) employeeFetch.getAddress();
                                                                                                                                                                //} else if (objectStatusFormula.equals(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM])){
                                                                                                                                                                //    value = (String) employeeFetch.getEmployeeNum();
                                                                                                                                                                //} else if (objectStatusFormula.equals(PstEmployee.fieldNames[PstEmployee.FLD_NPWP])){
                                                                                                                                                                //    value = (String) employeeFetch.getNpwp();
                                                                                                                                                                //} else if (objectStatusFormula.equals(PstEmpDocList.fieldNames[PstEmpDocList.FLD_JOB_DESC])){
                                                                                                                                                                //    value = (String) empDocList.getJob_desc();
                                                                                                                                                                //} else if (objectStatusFormula.equals(PstEmpDocList.fieldNames[PstEmpDocList.FLD_ASSIGN_AS])){
                                                                                                                                                                //    value = (String) empDocList.getAssign_as();
                                                                                                                                                                //} else if (objectStatusFormula.equals(PstEmployee.fieldNames[PstEmployee.FLD_BPJS_NO])){
                                                                                                                                                                //    value = (String) employeeFetch.getBpjs_no();
                                                                                                                                                                //}
                                                                                                                                                        } else {
                                                                                                                                                            System.out.print("Selain Object Employee belum bisa dipanggil");
                                                                                                                                                        }

                                                                                                                                                        stringTrReplace = stringTrReplace+value;  

                                                                                                                                                        residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                                                                                                                                                        endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                                                                                                      }while(endPositionOfFormula > 0);



                                                                                                                                                residuOfsubStringOfTr2 = residuOfsubStringOfTr2.substring(endPositionOfTd, residuOfsubStringOfTr2.length());
                                                                                                                                                startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                                                                                                                                endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                                                                                                                              jumlahtd = jumlahtd + 1 ;

                                                                                                                                              stringTrReplace = stringTrReplace+"</td>"; 
                                                                                                                                              }while(endPositionOfTd > 0);




                                                                                                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("<tr>"+subStringOfTr2+"</tr>", stringTrReplace); 
                                                                                                                                                tanpaeditor = tanpaeditor.replace("<tr>"+subStringOfTr2+"</tr>", stringTrReplace); 
                                                                                                                                             //tutup perulanganya

                                                                                                                                              //setelah baca td maka akan membuat td baru... disini

                                                                                                                                               residueOfTextString = residueOfTextString.substring(endPositionOfTable, residueOfTextString.length());

                                                                                                                                              startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                                                                                                                                              endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);

                                                                                                                                          }  while ( endPositionOfTable > 0);

                                                                                                                                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");                                                                     
                                                                                                                                        tanpaeditor = tanpaeditor.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");       

                                                                                                                                } else if  (objectType.equals("FIELD") && objectStatusField.equals("AUTO")){
                                                                                                                                        //String field = "<input type=\"text\" name=\""+ subString +"\" value=\"\">";
                                                                                                                                        Date newd = new Date();
                                                                                                                                        String field = "04/KEP/BPD-PMT/"+newd.getMonth()+"/"+newd.getYear();
                                                                                                                                        empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", field); 
                                                                                                                                        tanpaeditor = tanpaeditor.replace("${"+subString+"}", field); 

                                                                                                                                } else if  (objectType.equals("FIELD")){
                                                                                                                                    if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("TEXT"))){
                                                                                                                                        //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"add")+"</a></br>";
                                                                                                                                        //mpDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                                                                                                        //tanpaeditor = tanpaeditor.replace("${"+subString+"}", (hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):" ")); 
                                                                                                                                    } else if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("DATE"))){
                                                                                                                                        //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"add")+"</a></br>";
                                                                                                                                        //empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                                                                                                        //tanpaeditor = tanpaeditor.replace("${"+subString+"}", (hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):" ")); 
                                                                                                                                    } else if ((objectClass.equals("EMPDOCFIELD"))){
                                                                                                                                        //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(empDOcFecthH.get(objectName) != null?(String)empDOcFecthH.get(objectName):"add")+"</a></br>";
                                                                                                                                        //empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", ""+empDOcFecthH.get(objectStatusField) ); 
                                                                                                                                        //tanpaeditor = tanpaeditor.replace("${"+subString+"}", ""+empDOcFecthH.get(objectStatusField) ); 
                                                                                                                                    }

                                                                                                                                }

                                                                                                                                } else if (!objectName.equals("") && objectType.equals("") && objectClass.equals("") && objectStatusField.equals("")) {
                                                                                                                                      //String obj = ""+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"-");
                                                                                                                                      //empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"}", obj);
                                                                                                                                      //tanpaeditor = tanpaeditor.replace("${"+objectName+"}", obj);
                                                                                                                                }
                                                                                                                                stringResidual = stringResidual.substring(endPosition, stringResidual.length());
                                                                                                                                objectDocumentDetail.setStartPosition(startPosition);
                                                                                                                                objectDocumentDetail.setEndPosition(endPosition);
                                                                                                                                objectDocumentDetail.setText(subString);
                                                                                                                                vNewString.add(objectName);


                                                                                                                                //mengecek apakah masih ada sisa
                                                                                                                                startPosition = stringResidual.indexOf("${") + "${".length();
                                                                                                                                endPosition = stringResidual.indexOf("}", startPosition);
                                                                                                                         } while ( endPosition > 0);


                                                                                                     %>
                                                                                
                                                                                <form name="frmdocMasterAction" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_docMasterAction_id" value="<%=oidDocMasterAction%>">
                                                                                    <input type="hidden" name="<%=frmDocMasterAction.fieldNames[frmDocMasterAction.FRM_FIELD_DOC_MASTER_ID]%>" value="<%=DocMasterId%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <% if (listDocMasterAction.size() > 0 ) { 
                                                                                              
                                                                                            for (int i = 0; i < listDocMasterAction.size(); i++){
                                                                                               DocMasterAction docMasterAction1 =  (DocMasterAction) listDocMasterAction.get(i);
                                                                                        %>
                                                                                        
                                                                                        <% if (oidDocMasterAction == docMasterAction1.getOID() && iCommand == Command.EDIT){ %>
                                                                                            <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table >
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >ACTION </td>
                                                                                                        <td height="14" valign="middle" colspan="3" ><%= ControlCombo.draw(frmDocMasterAction.fieldNames[frmDocMasterAction.FRM_FIELD_ACTION_NAME], "formElemen" ,null, ""+(!docMasterAction1.getActionName().equals("") ?docMasterAction1.getActionName():""), DocMasterActionClass.getActionKey(), DocMasterActionClass.getActionKey(), "onChange=\"javascript:cmdActionKey()\"") %></td>
                                                                                                        <td height="14" valign="middle" colspan="3" ><a href="javascript:cmdEdit('<%=docMasterAction1.getOID()%>')" class="command">EDIT</a> <a href="javascript:cmdConfirmDelete('<%=docMasterAction1.getOID()%>')" class="command">DELETE</a> </td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >ACTION TITLE</td>
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; ><input type="text" name="<%=frmDocMasterAction.fieldNames[frmDocMasterAction.FRM_FIELD_ACTION_TITLE]%>"  value="<%=(!docMasterAction1.getActionTitle().equals("") ?docMasterAction1.getActionTitle():"")%>" class="elemenForm" size="30"></td>
                                                                                                    </tr>
                                                                                                    
                                                                                                    
                                                                                                    <% 
                                                                                                    String actionName1 = (!docMasterAction1.getActionName().equals("") ? docMasterAction1.getActionName(): "");
                                                                                                    if (!actionName1.equals("") ) { %>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >Obcject Name</td>
                                                                                                        <td height="14" valign="middle" colspan="3" ><%= drawList(actionName1, DocMasterId,vNewString,Command.EDIT, docMasterAction1.getOID())%></td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3"><a href="javascript:cmdSave()" >SAVE</a> </td>
                                                                                                    </tr>
                                                                                                    <% } %>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >_______________</td>
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >______________________________________________________________________</td>
                                                                                                    
                                                                                                     </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        
                                                                                        <% } else {%>
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table >
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; > ACTION </td>
                                                                                                        <td height="14" valign="middle" colspan="3" ><%=(!docMasterAction1.getActionName().equals("") ?docMasterAction1.getActionName():"-") %></td>
                                                                                                        <td height="14" valign="middle" colspan="3" ><a href="javascript:cmdEdit('<%=docMasterAction1.getOID()%>')" class="command">EDIT</a> <a href="javascript:cmdConfirmDelete('<%=docMasterAction1.getOID()%>')" class="command">DELETE</a> </td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >ACTION TITLE</td>
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; ><%=(!docMasterAction1.getActionTitle().equals("") ?docMasterAction1.getActionTitle():"")%></td>
                                                                                                    </tr>
                                                                                                    
                                                                                                    
                                                                                                    <% 
                                                                                                    String actionName = (!docMasterAction1.getActionName().equals("") ? docMasterAction1.getActionName():actionNameKey);
                                                                                                    if (!actionName.equals("") ) { %>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >Obcject Name</td>
                                                                                                        <td height="14" valign="middle" colspan="3" ><%= drawList(actionName, DocMasterId,vNewString, Command.NONE,docMasterAction1.getOID())%></td>
                                                                                                    </tr>
                                                                                                  
                                                                                                    <% } %>
                                                                                                     <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >_______________</td>
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >______________________________________________________________________</td>
                                                                                                    
                                                                                                     </tr>
                                                                                                     
                                                                                                     
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <% } %>
                                                                                        
                                                                                        <% 
                                                                                          }  
                                                                                          } %>
                                                                                        
                                                                                        <% if (iCommand == Command.ADD ){ %>
                                                                                        
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table >
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >ACTION </td>
                                                                                                        <td height="14" valign="middle" colspan="3" ><%= ControlCombo.draw(frmDocMasterAction.fieldNames[frmDocMasterAction.FRM_FIELD_ACTION_NAME], "formElemen" ,null, ""+(!docMasterAction.getActionName().equals("") ?docMasterAction.getActionName():actionNameKey), DocMasterActionClass.getActionKey(), DocMasterActionClass.getActionKey(), "onChange=\"javascript:cmdActionKey()\"") %></td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >ACTION TITLE</td>
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; ><input type="text" name="<%=frmDocMasterAction.fieldNames[frmDocMasterAction.FRM_FIELD_ACTION_TITLE]%>"  value="<%=(!docMasterAction.getActionTitle().equals("") ?docMasterAction.getActionTitle():"")%>" class="elemenForm" size="30"></td>
                                                                                                    </tr>
                                                                                                    
                                                                                                    
                                                                                                    <% 
                                                                                                    String actionName = (!docMasterAction.getActionName().equals("") ? docMasterAction.getActionName():actionNameKey);
                                                                                                    if (!actionName.equals("") ) { %>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >Obcject Name</td>
                                                                                                        <td height="14" valign="middle" colspan="3" ><%= drawList(actionName, DocMasterId,vNewString,Command.ADD, 0)%></td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3"><a href="javascript:cmdSave()" >SAVE</a> </td>
                                                                                                    </tr>
                                                                                                    <% } %>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >_______________</td>
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >______________________________________________________________________</td>
                                                                                                    
                                                                                                     </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        
                                                                                        <% } %>
                                                                                        
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table >
                                                                                                   
                                                                                                   <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" white-space: nowrap; >ADD</td>
                                                                                                        <td height="14" valign="middle" colspan="3" >
                                                                                                                        <a href="javascript:cmdAdd()" class="command">Add
                                                                                                                            New </a> </td>
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

