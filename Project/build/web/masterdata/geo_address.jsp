<%@page language = "java" %>
<%
            /*
             * Page Name  		:  kecamatan.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: Kartika
             * @version  		: 01
             */

            /*******************************************************************
             * Page Description 	: [project description ... ]
             * Imput Parameters 	: [input parameter ...]
             * Output 			: [output ...]
             *******************************************************************/
%>
<!-- package java -->
<%@page import = "java.util.*" %>
<!-- package dimata -->
<%@page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@page import = "com.dimata.gui.jsp.*" %>
<%@page import = "com.dimata.qdep.form.*" %>
<!--package HRIS -->
<%@page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@page import = "com.dimata.harisma.form.masterdata.*" %>
<%@page import = "com.dimata.harisma.form.employee.FrmEmployee" %>
<%@page import = "com.dimata.harisma.entity.admin.*" %>
<%@include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DEPARTMENT);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->

<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");            
            
            long oidNegara = FRMQueryString.requestLong(request, FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_NEGARA]);
            long oidProvinsi = FRMQueryString.requestLong(request, FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_PROPINSI]);
            long oidKabupaten = FRMQueryString.requestLong(request, FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KABUPATEN]);            
            long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
            long oidKecamatan = FRMQueryString.requestLong(request, FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KECAMATAN]);            
            String employee = FRMQueryString.requestString(request, "employee");            
            
            String formName = FRMQueryString.requestString(request,"formName");
            int addresstype = FRMQueryString.requestInt(request,"addresstype");
            String sKecamatan ="";
            String sKabupaten ="";
            String sProvinsi ="";
            String sNegara ="";
                        
            
            /*variable declaration*/
            int recordToGet = 10;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;

            CtrlKecamatan ctrlKecamatan = new CtrlKecamatan(request);

            ControlLine ctrLine = new ControlLine();

            /*switch statement */
            iErrCode = ctrlKecamatan.action(iCommand, oidKecamatan);
            /* end switch*/
            FrmKecamatan frmKecamatan = ctrlKecamatan.getForm();

            Kecamatan kecamatan = ctrlKecamatan.getKecamatan();
            //Provinsi provinsi = ctrlProvinsi.getPropinsi();
            msgString = ctrlKecamatan.getMessage();
            int commandRefresh = FRMQueryString.requestInt(request, "commandRefresh");
            
            kecamatan.setIdNegara(oidNegara);
            kecamatan.setIdPropinsi(oidProvinsi);
            kecamatan.setIdKabupaten(oidKabupaten);                
            kecamatan.setOID(oidKecamatan);    
            String geoAddress = "";
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Select Geo Address</title>
        <script language="JavaScript">
            
            function selectGeo(geoAddress){
                
                oidNegara= document.frmgeoaddress.<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_NEGARA]%>.value;
                oidProvinsi = document.frmgeoaddress.<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_PROPINSI ]%>.value;
                oidKabupaten = document.frmgeoaddress.<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KABUPATEN]%>.value;
                oidKecamatan = document.frmgeoaddress.<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KECAMATAN]%>.value;                
               
                <% if(addresstype==1) { %>
               
                    self.opener.document.<%=formName%>.<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_COUNTRY_ID]%>.value = oidNegara;
                    self.opener.document.<%=formName%>.<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_PROVINCE_ID]%>.value = oidProvinsi;
                    self.opener.document.<%=formName%>.<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_REGION_ID]%>.value = oidKabupaten;
                    self.opener.document.<%=formName%>.<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_SUBREGION_ID]%>.value = oidKecamatan;                    
                   
                    self.opener.document.<%=formName%>.geo_address.value = geoAddress;                                        
                <%} else { %>
                    self.opener.document.<%=formName%>.<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_COUNTRY_ID]%>.value = oidNegara;
                    self.opener.document.<%=formName%>.<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_PROVINCE_ID]%>.value = oidProvinsi;
                  
                    self.opener.document.<%=formName%>.<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_REGION_ID]%>.value = oidKabupaten;
               
                    self.opener.document.<%=formName%>.<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_SUBREGION_ID]%>.value = oidKecamatan;                    
                 
                    self.opener.document.<%=formName%>.geo_address_pmnt.value = geoAddress;                                        
                <%}%>
                
                self.close();                               
            }
            
            function cmdUpdateKec(){
                document.frmgeoaddress.command.value="<%=iCommand%>";
                document.frmgeoaddress.commandRefresh.value= "1";
                document.frmgeoaddress.action="geo_address.jsp";
                document.frmgeoaddress.submit();
            }
           
            function fnTrapKD(){
                //alert(event.keyCode);
                switch(event.keyCode)         {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break        ;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break        ;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break        ;
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
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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

    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
                                                    Select Geo <%=(addresstype==1?"":"Permanent") %> Address for <%=employee %><!-- #EndEditable -->
                                                </strong></font>
                                        </td>
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
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frmgeoaddress" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">                                                                                    
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="commandRefresh" value= "0">                                                                                    
                                                                                    <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">  
                                                                                    <input type="hidden" name="formName" value="<%=formName%>">  
                                                                                    <input type="hidden" name="addresstype" value="<%=addresstype%>">
                                                                                    <input type="hidden" name="employee" value="<%=employee%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">                                                                                        
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8" valign="middle" colspan="3">
                                                                                                <% {%>
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr>
                                                                                                        <td height="100%">
                                                                                                            <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="18%">Country</td>
                                                                                                                    <td width="82%"><%
                                                                                                                        Vector neg_value = new Vector(1, 1);
                                                                                                                        Vector neg_key = new Vector(1, 1);
                                                                                                                        neg_value.add("0");
                                                                                                                        neg_key.add("select ...");
                                                                                                                        Vector listNeg = PstNegara.list(0, 300, "", " NAMA_NGR ");
                                                                                                                        for (int i = 0; i < listNeg.size(); i++) {
                                                                                                                            Negara neg = (Negara) listNeg.get(i);
                                                                                                                            neg_key.add(neg.getNmNegara());
                                                                                                                            if(kecamatan.getIdNegara()==neg.getOID()){
                                                                                                                                geoAddress = geoAddress+ neg.getNmNegara();
                                                                                                                            }
                                                                                                                            neg_value.add(String.valueOf(neg.getOID()));                                                                                                                            
                                                                                                                        }
                                                                                                                        if(kecamatan.getIdNegara()==0){
                                                                                                                                geoAddress = geoAddress+ "-";
                                                                                                                        }
                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_NEGARA], "formElemen", null, "" + kecamatan.getIdNegara(), neg_value, neg_key, "onChange=\"javascript:cmdUpdateKec()\"")%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="18%">Province</td>
                                                                                                                    <td width="82%"><%
                                                                                                                        Vector pro_value = new Vector(1, 1);
                                                                                                                        Vector pro_key = new Vector(1, 1);
                                                                                                                        Provinsi pro = new Provinsi();
                                                                                                                        pro_value.add("0");
                                                                                                                        pro_key.add("select ...");
                                                                                                                        //Vector listPro = PstProvinsi.list(0, 0, "", " NAMA_PROP ");
                                                                                                                        String strWhere = PstProvinsi.fieldNames[PstProvinsi.FLD_ID_NEGARA] + "=" +kecamatan.getIdNegara();
                                                                                                                        Vector listPro = PstProvinsi.list(0, 300, strWhere, "NAMA_PROP");
                                                                                                                        boolean oidProvOk = false;
                                                                                                                        for (int i = 0; i < listPro.size(); i++) {
                                                                                                                            Provinsi prov = (Provinsi) listPro.get(i);
                                                                                                                            pro_key.add(prov.getNmProvinsi());
                                                                                                                            pro_value.add(String.valueOf(prov.getOID()));
                                                                                                                            if(kecamatan.getIdPropinsi()==prov.getOID()){
                                                                                                                                geoAddress = geoAddress+ ","+ prov.getNmProvinsi();
                                                                                                                            }
                                                                                                                            if(prov.getOID()==kecamatan.getIdPropinsi()){
                                                                                                                                oidProvOk=true;
                                                                                                                            }
                                                                                                                        }
                                                                                                                        if(!oidProvOk){
                                                                                                                            kecamatan.setIdPropinsi(0);
                                                                                                                            oidProvinsi=0;
                                                                                                                        }
                                                                                                                        if(kecamatan.getIdPropinsi()==0){
                                                                                                                                geoAddress = geoAddress+ ",-";
                                                                                                                            }
                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_PROPINSI], "formElemen", null, "" + kecamatan.getIdPropinsi(), pro_value, pro_key, "onChange=\"javascript:cmdUpdateKec()\"")%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="18%">Regency</td>
                                                                                                                    <td width="82%"><%
                                                                                                                        Vector kab_value = new Vector(1, 1);
                                                                                                                        Vector kab_key = new Vector(1, 1);
                                                                                                                        kab_value.add("0");
                                                                                                                        kab_key.add("select ...");
                                                                                                                        //Vector listKab = PstKabupaten.list(0, 0, "", " NAMA_KAB ");
                                                                                                                        String strWhereKab = PstKabupaten.fieldNames[PstKabupaten.FLD_ID_PROPINSI] + "=" + kecamatan.getIdPropinsi();
                                                                                                                        Vector listKab = PstKabupaten.list(0, 300, strWhereKab, "NAMA_KAB");
                                                                                                                        boolean oidKabOk = false;
                                                                                                                        for (int i = 0; i < listKab.size(); i++) {
                                                                                                                            Kabupaten kab = (Kabupaten) listKab.get(i);
                                                                                                                            kab_key.add(kab.getNmKabupaten());
                                                                                                                            kab_value.add(String.valueOf(kab.getOID()));
                                                                                                                            if(kecamatan.getIdKabupaten()==kab.getOID()){
                                                                                                                                geoAddress = geoAddress+ ","+ kab.getNmKabupaten();
                                                                                                                            }
                                                                                                                            if(oidKabupaten==kab.getOID()){
                                                                                                                              oidKabOk=true;  
                                                                                                                            }
                                                                                                                        }
                                                                                                                        if(!oidKabOk){
                                                                                                                            oidKabupaten=0;
                                                                                                                            kecamatan.setIdKabupaten(0);
                                                                                                                        }
                                                                                                                        if(kecamatan.getIdKabupaten()==0){
                                                                                                                                geoAddress = geoAddress+ ",-";
                                                                                                                            }
                                                                                                                        %> <%= ControlCombo.draw(frmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KABUPATEN], "formElemen", null, "" + kecamatan.getIdKabupaten(), kab_value, kab_key, "onChange=\"javascript:cmdUpdateKec()\"")%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="18%">Sub Regency</td>
                                                                                                                    <td width="82%"><%                                                                                                                    
                                                                                                                    
                                                                                                                        Vector kec_value = new Vector(1, 1);
                                                                                                                        Vector kec_key = new Vector(1, 1);
                                                                                                                        kec_value.add("0");
                                                                                                                        kec_key.add("select ...");
                                                                                                                        
                                                                                                                        String whereClause = "k." + PstKabupaten.fieldNames[PstKabupaten.FLD_ID_KABUPATEN]+"="+oidKabupaten;
                                                                                                                        String orderClause = "k." + PstKabupaten.fieldNames[PstKabupaten.FLD_NM_KABUPATEN] + ", kec." + PstKecamatan.fieldNames[PstKecamatan.FLD_NM_KECAMATAN];
                                                                                                                        Vector listKecamatan = new Vector(1, 1);                                                                                                                        
                                                                                                                        listKecamatan = PstKecamatan.listJoinKec(0, 300, whereClause, orderClause);                                                                                                                        
                                                                                                                        
                                                                                                                        for (int i = 0; i < listKecamatan.size(); i++) {
                                                                                                                            Kecamatan kec = (Kecamatan) listKecamatan.get(i);
                                                                                                                            kec_key.add(kec.getNmKecamatan());
                                                                                                                            kec_value.add(String.valueOf(kec.getOID()));
                                                                                                                            if(kecamatan.getOID ()==kec.getOID()){
                                                                                                                                geoAddress = geoAddress+ ","+ kec.getNmKecamatan();
                                                                                                                            }
                                                                                                                        }
                                                                                                                        if(kecamatan.getOID ()==0){
                                                                                                                                geoAddress = geoAddress+ ",-";
                                                                                                                            }
                                                                                                                        %> <%= ControlCombo.draw(frmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KECAMATAN], "formElemen", null, "" + kecamatan.getOID(), kec_value, kec_key, "onChange=\"javascript:cmdUpdateKec()\"")%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top" >
                                                                                                        <td class="command">
                                                                                                            &nbsp; <a href="javascript:selectGeo('<%=geoAddress%>')">Select <%=(addresstype==1?"":"Permanent") %> Geo Address</a>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                                <%}%>
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
        </table>
    </body>
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
                //var oBody = document.body;
                //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>
