<%-- 
    Document   : location
    Created on : Feb 24, 2014, 3:47:27 PM
    Author     : Satrya Ramayu
--%>

<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.payroll.PayGeneral"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayGeneral"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.location.FrmLocation"%>
<%@page import="com.dimata.harisma.form.masterdata.location.CtrlLocation"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_LOCATION_OUTLET, AppObjInfo.OBJ_LOCATION_OUTLET); %>
<%@ include file = "../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!    public static String formatNumberList = "#,###";
    public static String formatNumberEdit = "##.###";

    /* this constant used to list text of listHeader */
    public static final String textListHeader[][] = {
        {"No", "Kode", "Nama", "Alamat", "Telepon", "Fax", "Kontak Person", "E-mail", "Website", "Locasi Induk", "Tipe", "Kontak Perusahaan", "Keterangan", "Tax Persentase", "Service Percentase", "Tax & Service Standart", "Persentasi Distribusi PO (%)", "Nama Perusahaan","Color Location","Sub-Regency"},
        {"No", "Code", "Name", "Address", "Phone", "Fax", "Person Name", "E-mail", "Website", "Parent Location", "Type", "Contact Link", "Description", "Tax Persentase", "Service Percentase", "Tax & Service Default", "Persentasi Distribusi PO (%)","Nama Perusahaan","Warna Lokasi","Kecamatan"}
    };
    public static final String textListTitleHeader[][] = {
        {"Lokasi Barang"},
        {"Goods Location"}
    };

    public String drawList(int language, Vector objectClass, long locationId, String approot, int start, int recordToGet) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tabbg");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader(textListHeader[language][0], "3%");
        ctrlist.addHeader(textListHeader[language][1], "10%");
        ctrlist.addHeader(textListHeader[language][2], "15%");
        ctrlist.addHeader(textListHeader[language][3], "15%");
        ctrlist.addHeader(textListHeader[language][4], "10%");
        ctrlist.addHeader(textListHeader[language][5], "10%");
        ctrlist.addHeader(textListHeader[language][6], "10%");
        ctrlist.addHeader(textListHeader[language][17], "10%");
        ctrlist.addHeader(textListHeader[language][10], "10%");
        ctrlist.addHeader(textListHeader[language][16], "10%");
        ctrlist.addHeader(textListHeader[language][18], "10%");
        ctrlist.addHeader(textListHeader[language][19], "10%");

        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        Vector lstLinkDataa = ctrlist.getLinkData();
        ctrlist.reset();

        int index = -1;
        if (start < 0) {
            start = 0;
        }

        for (int i = 0; i < objectClass.size(); i++) {
            Location location = (Location) objectClass.get(i);

            Vector rowx = new Vector();
            if (locationId == location.getOID()) {
                index = i;
            }

            start = start + 1;
            rowx.add("" + start);
            rowx.add("<a href=\"javascript:cmdEdit('" + location.getOID() + "')\">" + location.getCode() + "</a>");
            rowx.add(cekNull(location.getName()));
            rowx.add(cekNull(location.getAddress()));
            rowx.add(cekNull(location.getTelephone()));
            rowx.add(cekNull(location.getFax()));
            rowx.add(cekNull(location.getPerson()));
            //update by fitra

            rowx.add(cekNull(location.getCompanyName()));


            rowx.add(PstLocation.fieldLocationType[location.getType()]);
            rowx.add("" + location.getPersentaseDistributionPurchaseOrder());
            rowx.add("<table width=\"30\"><tr><td "+(location.getColorLocation()!=null && location.getColorLocation().length()>0?  "bgcolor=\"#"+location.getColorLocation()+"\"" :"")+">&nbsp;</td></tr></table>");
            rowx.add(""+location.getSubRegencyName());
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(location.getOID()));
        }
        return ctrlist.draw();
    }

    public String cekNull(String val) {
        if (val == null) {
            val = "";
        }
        return val;
    }
%>

<%
    /* get data from request form */
    int iCommand = FRMQueryString.requestCommand(request);
    
    int startLocation = FRMQueryString.requestInt(request, "start_location");
    
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidLocation = FRMQueryString.requestLong(request, "hidden_location_id");
    long oidMinimum = FRMQueryString.requestLong(request, "hidden_mat_minimum_id");
    SESS_LANGUAGE = 1;
    String locationTitle = textListTitleHeader[SESS_LANGUAGE][0]; //com.dimata.common.jsp.JspInfo.txtMaterialInfo[SESS_LANGUAGE][com.dimata.common.jsp.JspInfo.LOCATION];
//String minimumTitle = com.dimata.material.jsp.JspInfo.txtMaterialInfo[SESS_LANGUAGE][com.dimata.material.jsp.JspInfo.MATERIAL_MINIMUM];

    /* variable declaration */
    int recordToGet = 5;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = PstLocation.fieldNames[PstLocation.FLD_CODE] + "," + PstLocation.fieldNames[PstLocation.FLD_NAME];

    /* ControlLine */
    ControlLine ctrLine = new ControlLine();

    /* Control LOcation */
    CtrlLocation ctrlLocation = new CtrlLocation(request);
    FrmLocation frmLocation = ctrlLocation.getForm();
    iErrCode = ctrlLocation.action(iCommand, oidLocation);
    Location location = ctrlLocation.getLocation();
    msgString = ctrlLocation.getMessage();




    /* get start value for list location */
    if (iCommand == Command.SAVE && iErrCode == FRMMessage.NONE) {
        startLocation = PstLocation.findLimitStart(location.getOID(), recordToGet, whereClause);
    }
//update by fitra
    int vectSize = PstLocation.getCountJoin(whereClause);
    if (iCommand == Command.FIRST || iCommand == Command.PREV || iCommand == Command.NEXT || iCommand == Command.LAST) {
        startLocation = ctrlLocation.actionList(iCommand, startLocation, vectSize, recordToGet);
    }

    /* get record to display */
    Vector listLocation = new Vector(1, 1);

//update by fitra
    listLocation = PstLocation.listJoin(startLocation, recordToGet, whereClause, orderClause);
    if (listLocation.size() < 1 && startLocation > 0) {
        if (vectSize - recordToGet > recordToGet) {
            startLocation = startLocation - recordToGet;
        } else {
            startLocation = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST;
        }
        //update by fitra
        listLocation = PstLocation.listJoin(startLocation, recordToGet, whereClause, orderClause);
    }

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" --><!-- DW6 -->
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>Harisma - Location</title>
        <script language="JavaScript">
            /*------------- start location function ---------------*/
            function cmdAdd()
            {
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.hidden_location_id.value="0";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.ADD%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="location.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }

            function cmdAsk(oidLocation)
            {
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.hidden_location_id.value=oidLocation;
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.ASK%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="location.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }

            function cmdConfirmDelete(oidLocation)
            {
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.hidden_location_id.value=oidLocation;
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.DELETE%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="location.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }

            function cmdSave()
            {
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.SAVE%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="location.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }

            function cmdEdit(oidLocation)
            {
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.hidden_location_id.value=oidLocation;
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.EDIT%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="location.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }

            function cmdCancel(oidLocation)
            {
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.hidden_location_id.value=oidLocation;
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.EDIT%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="location.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }

            function cmdBack()
            {
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.BACK%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="location.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }

            function cmdListFirst()
            {
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.prev_command.value="<%=Command.FIRST%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="location.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }

            function cmdListPrev()
            {
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.PREV%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.prev_command.value="<%=Command.PREV%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="location.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }

            function cmdListNext()
            {
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.prev_command.value="<%=Command.NEXT%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="location.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }

            function cmdListLast()
            {
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.LAST%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.prev_command.value="<%=Command.LAST%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="location.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }

            function srcContact()
            {
                window.open("contact_search.jsp?command=<%=Command.FIRST%>&contact_name="+document.<%=FrmLocation.FRM_NAME_LOCATION%>.contact_name.value,"group", "height=400,width=600,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes");
            }
            /*------------- end location function ---------------*/


            /*------------- start vendor price function -----------------*/
            function addMinimumQty()
            {
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.ADD%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="minimum_qty.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }

            function editMinimumQty(locationId,minimumOid)
            { 
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.hidden_location_id.value=locationId;
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.hidden_mat_minimum_id.value=minimumOid;
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.command.value="<%=Command.EDIT%>";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.action="minimum_qty.jsp";
                document.<%=FrmLocation.FRM_NAME_LOCATION%>.submit();
            }
            /*------------- end vendor price function -----------------*/


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
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        
         <link href="<%=approot%>/stylesheets/colorpicker.css" type="text/css" rel="stylesheet"> 
         
        <script type="text/javascript" src="<%=approot%>/stylescript/jscolor.js"></script>
       
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
        
    </head> 
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
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
                    <!-- #BeginEditable "content" -->
                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr>
                            <td width="100%">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td height="20">
                                            <font color="#FF6600" face="Arial"><strong>
                                                Master Data &gt; Location
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

                                                                                
                                                                                <form name="<%=FrmLocation.FRM_NAME_LOCATION%>" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start_location" value="<%=startLocation%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_location_id" value="<%=oidLocation%>">
                                                                                    <input type="hidden" name="hidden_mat_minimum_id" value="<%=oidMinimum%>">			  
                                                                                    <input type="hidden" name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_CONTACT_ID]%>" value="<%=location.getContactId()%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td height="8" valign="middle" colspan="3"> 
                                                                                                <table width="100%">
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td height="8"  colspan="3"> 
                                                                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td height="14" valign="middle" colspan="3" class="comment">
                                                                                                                        &nbsp;<u><%=SESS_LANGUAGE == com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT ? "Daftar " + locationTitle : locationTitle + " List"%></u>
                                                                                                                    </td>                      
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td height="22" valign="middle" colspan="3">
                                                                                                                        <%if (listLocation != null && listLocation.size() > 0) {%>
                                                                                                                        <%= drawList(SESS_LANGUAGE, listLocation, oidLocation, approot, startLocation, recordToGet)%> 
                                                                                                                        <%} else {%>
                                                                                                                        record not found
                                                                                                                        <%}%>
                                                                                                                    </td>
                                                                                                                </tr>
                    <tr align="left" valign="top"> 
                        <td height="8" align="left" colspan="3" class="command"> 
                          <span class="command"> 
                          <% 
                            int cmd = 0;
                            if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST){
                                    cmd =iCommand; 
                            }else{
                                    if(iCommand==Command.NONE || prevCommand==Command.NONE)
                                          cmd = Command.FIRST;
                                    else 
                                          cmd =prevCommand; 
                            } 						  
                          ctrLine.setLocationImg(approot+"/images");
                          ctrLine.initDefault();							
                          out.println(ctrLine.drawImageListLimit(cmd,vectSize,startLocation,recordToGet));
			%>
                          </span>
			</td>
                      </tr>
                      <%
                        if(iCommand==Command.LIST || iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand == Command.LAST
                              || iCommand==Command.NONE
                              || iCommand==Command.BACK 
                              || (iCommand==Command.SAVE&&iErrCode==0) || (iCommand==Command.DELETE && iErrCode==0)){
                     %>					  
                      <tr align="left" valign="top"> 
                        <td> 
                          <%if(((iCommand!=Command.ADD)&&(iCommand!=Command.EDIT)&&(iCommand!=Command.ASK))||(iCommand==Command.SAVE&&iErrCode==0) || (iCommand==Command.DELETE && iErrCode==0)){%>
                          <table cellpadding="0" cellspacing="0" border="0">
                            <tr> 
                              <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                              <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="<%=ctrLine.getCommand(SESS_LANGUAGE,locationTitle,ctrLine.CMD_ADD,true)%>"></a></td>
                              <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                              <td height="22" valign="middle" colspan="3" width="172"><a href="javascript:cmdAdd()" class="command"><%=ctrLine.getCommand(SESS_LANGUAGE,locationTitle,ctrLine.CMD_ADD,true)%></a></td>
                            </tr>
                          </table>
                          <%}%>
                        </td>
                      </tr>
                    <%}%>
                                                                                                            </table>
                                                                                                           </td>
                                                                                                        </tr>
<%
                        if((iCommand ==Command.ADD)
                              ||(iCommand==Command.EDIT)
                              ||(iCommand==Command.ASK)					  
                          ||((iCommand==Command.SAVE) && iErrCode>0) || (iCommand==Command.DELETE && iErrCode>0))
                        {
                      %>                                                                                                        
<tr align="left" valign="top"> 
                  <td height="8" valign="middle" colspan="3"> 
                    
                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                      <tr> 
                        <td colspan="3" class="comment" height="30"><u><%=SESS_LANGUAGE==com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT ? "Editor "+locationTitle : locationTitle+" Editor"%></u></td>
                      </tr>
					                        <tr align="left"> 
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][1]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top"> 
                            <input type="text" name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_CODE] %>"  value="<%= location.getCode() %>" class="formElemen" size="20">
                                * <%= frmLocation.getErrorMsg(FrmLocation.FRM_FIELD_CODE) %></td>
                            </tr>
							
                      <tr align="left"> 
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][2]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top"> 
                                <input type="text" name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_NAME] %>"  value="<%= location.getName() %>" class="formElemen" size="40">
                                * <%= frmLocation.getErrorMsg(FrmLocation.FRM_FIELD_NAME) %></td>
                            </tr>
                            <tr align="left"> 
                              <td valign="top" width="17%"><%=textListHeader[SESS_LANGUAGE][3]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top"> 
                                <textarea name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_ADDRESS] %>" class="formElemen" cols="39" rows="2" wrap="VIRTUAL"><%= location.getAddress() %></textarea>
                                * <%= frmLocation.getErrorMsg(FrmLocation.FRM_FIELD_ADDRESS) %> </td>
                            </tr>
                            <tr align="left"> 
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][4]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top"> 
                                <input type="text" name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_TELEPHONE] %>"  value="<%= location.getTelephone() %>" class="formElemen" size="20">
                              </td>
                            </tr>
                            <tr align="left"> 
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][5]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top"> 
                                <input type="text" name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_FAX] %>"  value="<%= location.getFax() %>" class="formElemen" size="20">
                              </td>
                            </tr>
                            <tr align="left"> 
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][6]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top"> 
                                <input type="text" name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_PERSON] %>"  value="<%= location.getPerson() %>" class="formElemen" size="40">
                              </td>
                            </tr>
                            <tr align="left"> 
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][7]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top"> 
                                <input type="text" name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_EMAIL] %>"  value="<%= location.getEmail() %>" class="formElemen" size="30">
                              </td>
                            </tr>
                            <tr align="left"> 
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][8]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top"> 
                                <input type="text" name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_WEBSITE] %>"  value="<%= location.getWebsite() %>" class="formElemen" size="30">
                              </td>
                            </tr>
                            <tr align="left"> 
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][17]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top">
                                <% Vector company_value = new Vector();
                                   Vector company_key = new Vector();
                                       company_key.add("select");
                                       company_value.add(""+0);
                                       Vector vlistCompany = PstPayGeneral.list(0, 0, "","");
                                       if(vlistCompany!=null && vlistCompany.size()>0){
                                           for(int x=0; x<vlistCompany.size(); x++){
                                           PayGeneral company = (PayGeneral)vlistCompany.get(x);
                                               company_key.add(company.getCompanyName());
                                               company_value.add(""+company.getOID());
                                           }
                                       }
                                %>
                                <%= ControlCombo.draw(FrmLocation.fieldNames[FrmLocation.FRM_FIELD_COMPANY_ID], null, ""+location.getCompanyId(),company_value,company_key)%> 
                                * <%= frmLocation.getErrorMsg(FrmLocation.FRM_FIELD_COMPANY_ID) %>
                                 <%//= ControlCombo.draw(frmLocation.fieldNames[FrmLocation.FRM_FIELD_COMPANY_ID],null, sel_company, company_key, company_value,"","formElemen") %>
                              </td>
                            </tr>
                            <tr align="left">
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][10]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top"> 
                                <% Vector type_value = new Vector(1,1);
								   Vector type_key = new Vector(1,1);
								   String sel_type = ""+location.getType();
								   
								   type_value.add(PstLocation.fieldLocationType[PstLocation.TYPE_LOCATION_WAREHOUSE]);
								   type_key.add(""+PstLocation.TYPE_LOCATION_WAREHOUSE);
								   
								   type_value.add(PstLocation.fieldLocationType[PstLocation.TYPE_LOCATION_STORE]);
								   type_key.add(""+PstLocation.TYPE_LOCATION_STORE);						   								   
								   
								   /*
								   for(int i=0;i<PstLocation.fieldLocationType.length;i++){
										type_value.add(PstLocation.fieldLocationType[i]);
										type_key.add(""+i+"");
								   }
								   */								
							    %>
                                <%= ControlCombo.draw(frmLocation.fieldNames[FrmLocation.FRM_FIELD_TYPE],null, sel_type, type_key, type_value,"","formElemen") %> </td>
                            </tr>
                            <tr align="left"> 
                              <td valign="top" width="17%"><%=textListHeader[SESS_LANGUAGE][12]%><br>ID Mesin Data (Max. 3 Number)</td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top"> 
                                <textarea name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_DESCRIPTION] %>" class="formElemen" cols="39" rows="2" wrap="VIRTUAL"><%= location.getDescription() %></textarea>
                              </td>
                            </tr>
                            <%--add opie 13-06-2012 untuk penambahan percentase tax dan service --%>
                            <tr align="left">
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][13]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top">
                                <input type="text" name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_TAX_PERCENT] %>"  value="<%= location.getTaxPersen() %>" class="formElemen" size="30">
                              </td>
                            </tr>
                            <tr align="left">
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][14]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top">
                                <input type="text" name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_SERVICE_PERCENT] %>"  value="<%= location.getServicePersen() %>" class="formElemen" size="30">
                              </td>
                            </tr>
                            <tr align="left">
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][15]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top">
                                      <%
                                    Vector tsdValue = new Vector(1,1);
                                    Vector tsdKey = new Vector(1,1);

                                    tsdKey.add(PstLocation.tsdNames[SESS_LANGUAGE][PstLocation.TSD_INCLUDE_NOTCHANGABLE]);
                                    tsdValue.add(""+PstLocation.TSD_INCLUDE_NOTCHANGABLE);

                                    tsdKey.add(PstLocation.tsdNames[SESS_LANGUAGE][PstLocation.TSD_NOTINCLUDE_NOTCHANGABLE]);
                                    tsdValue.add(""+PstLocation.TSD_NOTINCLUDE_NOTCHANGABLE);

                                    tsdKey.add(PstLocation.tsdNames[SESS_LANGUAGE][PstLocation.TSD_INCLUDE_CHANGABLE]);
                                    tsdValue.add(""+PstLocation.TSD_INCLUDE_CHANGABLE);

                                    tsdKey.add(PstLocation.tsdNames[SESS_LANGUAGE][PstLocation.TSD_NOTINCLUDE_CHANGABLE]);
                                    tsdValue.add(""+PstLocation.TSD_NOTINCLUDE_CHANGABLE);

                                    %> <%=ControlCombo.draw(frmLocation.fieldNames[FrmLocation.FRM_FIELD_TAX_SERVICE_DEFAULT],"formElemen", null, ""+location.getTaxSvcDefault(), tsdValue, tsdKey, null)%></td>
                             
                            </tr>
                            <tr align="left">
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][16]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top">
                                <input type="text" name="<%=frmLocation.fieldNames[FrmLocation.FRM_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER] %>"  value="<%= location.getPersentaseDistributionPurchaseOrder() %>" class="formElemen" size="30">
                              </td>
                            </tr>
                            <%-- finish eyek 13-06-2012--%>
                            <tr align="left">
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][18]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top">
                                <input id='colorhover' class="color" name="<%=frmLocation.fieldNames[FrmLocation.FRM_FIELD_COLOR_LOCATION] %>" value="<%=location.getColorLocation() == null ? "c1e4ec" : location.getColorLocation()%>">
                               * <%= frmLocation.getErrorMsg(FrmLocation.FRM_FIELD_COLOR_LOCATION) %>
                              </td>
                            </tr>
                            
                            <tr align="left"> 
                              <td width="17%"><%=textListHeader[SESS_LANGUAGE][19]%></td>
                              <td width="1%" valign="top">:</td>
                              <td width="82%" valign="top">
                                <% Vector subRegency_value = new Vector();
                                   Vector subRegency_key = new Vector();
                                       subRegency_key.add("select");
                                       subRegency_value.add(""+0);
                                       Vector vlistSubRegency = PstKecamatan.list(0, 0, "",""); 
                                       if(vlistSubRegency!=null && vlistSubRegency.size()>0){
                                           for(int x=0; x<vlistSubRegency.size(); x++){
                                           Kecamatan kecamatan = (Kecamatan)vlistSubRegency.get(x);
                                               subRegency_key.add(kecamatan.getNmKecamatan());
                                               subRegency_value.add(""+kecamatan.getOID());
                                           }
                                       }
                                %>
                                <%= ControlCombo.draw(FrmLocation.fieldNames[FrmLocation.FRM_FIELD_SUB_REGENCY], "-select-", ""+location.getSubRegencyId(),subRegency_value,subRegency_key)%> 
                                 
                              </td>
                            </tr>

                    </table>
                  </td>
</tr>
 <%}%>
 <%if(((iCommand!=Command.SAVE) && (iCommand!=Command.ASK)) || iErrCode>0){%> 
<tr align="left" valign="top" > 
                        <td colspan="2" class="command"> 
                          <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80%");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidLocation + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidLocation + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidLocation + "')";
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setBackCaption("Back to List Location");
                                                                                                                ctrLine.setSaveCaption("Save Location");
                                                                                                                ctrLine.setConfirmDelCaption("Yes Delete Location");
                                                                                                                ctrLine.setDeleteCaption("Delete Location");

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
                      <%}%>
                      
					
                                                                                                    </table>
                                                                                                </td>
                                                                                            </tr>
                                                                                            
                                                                                        </table>
                                                                    </form>
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
        </table>
                         <!-- #EndEditable -->
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
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>

<!-- #EndTemplate --></html>

