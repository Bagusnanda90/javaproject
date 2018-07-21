<%-- 
    Document   : srclocation
    Created on : May 26, 2014, 3:19:01 PM
    Author     : Ramayu
--%>
<%@page import="com.dimata.harisma.form.masterdata.location.FrmLocation"%>
<%@page import="com.dimata.harisma.form.masterdata.location.CtrlLocation"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../main/javainit.jsp"%>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_LOCATION_OUTLET, AppObjInfo.OBJ_LOCATION_OUTLET);%>
<%@ include file = "../main/checkuser.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>

<%

    int iCommand = FRMQueryString.requestCommand(request);
    String source = FRMQueryString.requestString(request, "source");
    String locationName = FRMQueryString.requestString(request, "Location_Name");
    
    int startLocation = FRMQueryString.requestInt(request, "start_location");
    
    //int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidLocation = FRMQueryString.requestLong(request, "hidden_location_id");
    //long oidMinimum = FRMQueryString.requestLong(request, "hidden_mat_minimum_id");
    int SESS_LANGUAGE = 1;
    //String locationTitle = textListTitleHeader[SESS_LANGUAGE][0]; //com.dimata.common.jsp.JspInfo.txtMaterialInfo[SESS_LANGUAGE][com.dimata.common.jsp.JspInfo.LOCATION];
//String minimumTitle = com.dimata.material.jsp.JspInfo.txtMaterialInfo[SESS_LANGUAGE][com.dimata.material.jsp.JspInfo.MATERIAL_MINIMUM];

    /* variable declaration */
    int recordToGet = 10;
   // String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    if(locationName!=null && locationName.length()>0){
        whereClause = PstLocation.fieldNames[PstLocation.FLD_NAME] + "="+locationName; 
    }
    String orderClause = PstLocation.fieldNames[PstLocation.FLD_CODE] + "," + PstLocation.fieldNames[PstLocation.FLD_NAME];

    /* ControlLine */
    //ControlLine ctrLine = new ControlLine();

    /* Control LOcation */
    CtrlLocation ctrlLocation = new CtrlLocation(request);
    //FrmLocation frmLocation = ctrlLocation.getForm();
    iErrCode = ctrlLocation.action(iCommand, oidLocation);
    Location location = ctrlLocation.getLocation();
    //msgString = ctrlLocation.getMessage();




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
            //prevCommand = Command.FIRST;
        }
        //update by fitra
        listLocation = PstLocation.listJoin(startLocation, recordToGet, whereClause, orderClause);
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HARISMA - Search Location</title>
        <script language="JavaScript">

            function cmdUpdateDiv(){
                document.frmsrclocation.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrclocation.action="srclocation.jsp";
                document.frmsrclocation.submit();
            }

            function cmdUpdateDep(){
                document.frmsrclocation.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrclocation.action="srclocation.jsp"; 
                document.frmsrclocation.submit();
            }
            function cmdUpdatePos(){
                document.frmsrclocation.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrclocation.action="srclocation.jsp"; 
                document.frmsrclocation.submit();
            }

            function cmdSearch(){
                document.frmsrclocation.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrclocation.action="srclocation.jsp";
                document.frmsrclocation.submit();
            }


            function fnTrapKD(){
                if (event.keyCode == 13) {
                    document.all.aSearch.focus();
                    cmdSearch();
                }
            }

            function MM_swapImgRestore() { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a && i < a.length && (x=a[i]) && x.oSrc;i++) x.src=x.oSrc;
            }

            function MM_preloadImages() { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i < a.length; i++)
                        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                }

                function MM_findObj(n, d) { //v4.0
                    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0 && parent.frames.length) {
                        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                    if(!(x=d[n]) && d.all) x=d.all[n]; for (i=0;!x && i < d.forms.length;i++) x=d.forms[i][n];
                    for(i=0;!x && d.layers && i < d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                    if(!x && document.getElementById) x=document.getElementById(n); return x;
                }

                function MM_swapImage() { //v3.0
                    var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                        if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
                }
        </script>

        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 

        <link rel="stylesheet" href="../../styles/main.css" type="text/css">

        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">

    </head> 

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if(source==null || (source!=null && source.length()==0)){%> 
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 

                    <%@ include file = "../../main/header.jsp" %>

                </td>
            </tr> 

            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> 
                    <%@ include file = "../../main/mnmain.jsp" %>
                </td> 
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
        <%}%>
            <tr > 
                <td width="88%" valign="top" align="left" > 
                    <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
                        <tr> 
                            <td width="100%">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
                                    <tr> 
                                        <td height="20">
                                            <font color="#FF6600" face="Arial"><strong>

                                                Master Data &gt; Location &gt; Location Search
                                            </strong></font>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr> 
                                                                <td valign="top"> 
                                                                    <table  style="border:1px solid <%=garisContent%>" width="100%" cellspacing="1" cellpadding="1" class="tabbg" >
                                                                        <tr> 
                                                                            <td valign="top">
                                                                                 <!-- #BeginEditable "contenttitle" --> 
                                                                                 <table width="100%">
                                                                                     <tr>
                                                                                         <td width="17%" nowrap><div align="left">Nama Location</div></td>
                                                                                         <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                                                         <td width="83%">
                                                                                                <input type="text" name="Location_Name"  value="<%= locationName %>" class="elemenForm" onkeydown="javascript:fnTrapKD()">
                                                                                         </td>
                                                                                     </tr>
                                                                                     <tr> 
                                                                                        <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                                                        <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                                                        <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()">Search for Employee</a></td>
                                                                                     </tr>
                                                                                 </table>
                                                                                 <!-- #EndEditable --> 
                                                                            </td>
                                                                        </tr>
                                                                        <%if(iCommand==Command.LIST){%>
                                                                        <tr>
                                                                            <td>
                                                                                <%=drawList(SESS_LANGUAGE, listLocation, oidLocation, approot, startLocation, recordToGet)%>
                                                                            </td>
                                                                        </tr>
                                                                        <%}%>
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
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" >
                    <%@ include file = "../../main/footer.jsp" %>
                </td>
            </tr>
            <%}%>
        </table>
    </body>
</html>
