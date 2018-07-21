

<%@page import="com.dimata.harisma.form.koefisionposition.CtrlKoefisionPosition"%>
<%@page import="com.dimata.harisma.form.koefisionposition.FrmKoefisionPosition"%>
<%@page import="java.lang.String"%>
<%@page import="com.dimata.harisma.entity.koefisionposition.PstKoefisionPosition"%>
<%@page import="com.dimata.harisma.entity.koefisionposition.KoefisionPosition"%>

<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ page language="java" %>  

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->

<%@page import="java.util.Vector"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->

<%@ include file = "../main/javainit.jsp" %> 
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_KOEFISIEN_POSITION);%>
<%@ include file = "../main/checkuser.jsp" %>

<%
            //boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
            //boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
%>
<%

           
            long oidKoefisionPosition = FRMQueryString.requestLong(request, "oidKoefisionPosition");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            int iCommand = FRMQueryString.requestCommand(request);
            int startKoefisionPosition = FRMQueryString.requestInt(request, "startKoefisionPosition");

           
            int status = FRMQueryString.requestInt(request, "status");

            int iErrCode = FRMMessage.ERR_NONE;
            String msgString = "";
            ControlLine ctrLine = new ControlLine();
            //System.out.println("iCommand = " + iCommand);
             CtrlKoefisionPosition ctrlKoefisionPosition= new CtrlKoefisionPosition(request);
            iErrCode = ctrlKoefisionPosition.action(iCommand, oidKoefisionPosition);
            msgString = ctrlKoefisionPosition.getMessage();
            
            FrmKoefisionPosition frmKoefisionPosition = ctrlKoefisionPosition.getForm(); 
            KoefisionPosition koefisionPosition = ctrlKoefisionPosition.getCoefisionPosition(); 
            oidKoefisionPosition = koefisionPosition.getOID();
            Vector listKoefisionPosition = new Vector(); 
            PstKoefisionPosition pstKoefisionPosition = new PstKoefisionPosition(); 
            /*variable declaration*/
            int recordToGet = 10; 
           Vector listBenefit = new Vector(1, 1);
           
            String orderClause = " SORT_IDX ";
          
            int vectKoefisionPosition = pstKoefisionPosition.getCount("");
              if ((iCommand == Command.FIRST || iCommand == Command.PREV) ||
                    (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                if (startKoefisionPosition < 0) {
                    startKoefisionPosition = 0;
                }
              

                startKoefisionPosition = ctrlKoefisionPosition.actionList(iCommand, startKoefisionPosition, vectKoefisionPosition, recordToGet); 
               
            }
          
            //PayExecutive payExecutive = ctrLanguage.getLanguage();
            msgString = ctrlKoefisionPosition.getMessage();
             /* get record to display */
            if (startKoefisionPosition < 0) {
                startKoefisionPosition = 0;
            }
           
            listKoefisionPosition = pstKoefisionPosition.innerJoinList(startKoefisionPosition,recordToGet); 
               /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listKoefisionPosition.size() < 1 && startKoefisionPosition > 0) {
                if (vectKoefisionPosition - recordToGet > recordToGet) {
                    startKoefisionPosition = startKoefisionPosition - recordToGet; 
                } //go to Command.PREV
                else {
                    startKoefisionPosition = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                if (startKoefisionPosition < 0) {
                    startKoefisionPosition = 0;
                }
                listKoefisionPosition = pstKoefisionPosition.list(startKoefisionPosition, recordToGet, "", "");
            //listDeduction = PstPayComponent.list(startBenefit,recordToGet, whereClauseDeduction , orderClause);
            }
           
            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
          

            


%>

<%!
    public String drawList(int iCommand, FrmKoefisionPosition frmKoefisionPosition,Vector listKoefisionPosition,int status,long oidKoefisionPosition,KoefisionPosition koefisionPositionn,int limit) {
        System.out.println("Status  " + status);
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "2%");
        ctrlist.addHeader("Position", "7%");
        //ctrlist.addHeader("","");
        ctrlist.addHeader("Nilai Koefision Outlet", "20%");
        ctrlist.addHeader("Nilai Koefision DC", "20%");
       
       
         
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        Vector rowx = new Vector(1, 1);
        
        ctrlist.reset();
        int index = -1;
          int nmber =0;
        Vector vPosition = new Vector();
         Vector vOid = new Vector();
         Vector vValue = new Vector();
        vPosition = PstPosition.list(0, 0, "", "");
        if(vPosition!=null && vPosition.size()>0){
            vOid = new Vector();
            vValue = new Vector();
            vOid.add(""+0);
            vValue.add("select");
            for(int i=0;i < vPosition.size();i++){
                Position position =(Position)vPosition.get(i);
                vOid.add(""+position.getOID());
                vValue.add(position.getPosition());
            }
            
        }
      
        
        
        for (int i = 0; i < listKoefisionPosition.size(); i++) {
            KoefisionPosition koefisionPosition = (KoefisionPosition) listKoefisionPosition.get(i);  
            rowx = new Vector();
            if(i==0){
              nmber=1;  
            }else{
                nmber=nmber+1;
            }
            
            if (oidKoefisionPosition == koefisionPosition.getOID()) { 
                index = i;
            }
            if (index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)) {
                rowx.add(""+(limit+nmber));
                rowx.add(""+ControlCombo.draw(FrmKoefisionPosition.fieldNames[FrmKoefisionPosition.FRM_FLD_POSITION_ID], "formElemen", null, "" + koefisionPosition.getPositionId(), vOid, vValue)); 
                rowx.add("<input type=\"text\" name=\"" + FrmKoefisionPosition.fieldNames[FrmKoefisionPosition.FRM_FLD_NILAI_KOEFISION_OUTLET] + "\" value=\"" + koefisionPosition.getNilaiKoefisionOutlet() + "\" size=\"12\" class=\"elemenForm\">");
                rowx.add("<input type=\"text\" name=\"" + FrmKoefisionPosition.fieldNames[FrmKoefisionPosition.FRM_FLD_NILAI_KOEFISION_DC] + "\" value=\"" + koefisionPosition.getNilaiKoefisionDc() + "\" size=\"12\" class=\"elemenForm\">");
               
                
            } else {
              
                //System.out.println("aku cek");
                 rowx.add(""+(limit+nmber)); 
                rowx.add("<a href=\"javascript:cmdEdit('" + String.valueOf(koefisionPosition.getOID()) + "')\">" + koefisionPosition.getPosition() + "</a>"); 
                rowx.add("" + koefisionPosition.getNilaiKoefisionOutlet());
                rowx.add("" + koefisionPosition.getNilaiKoefisionDc()); 
                
               
                //update by satrya 20130207
               
            }
            lstData.add(rowx);
        }
        rowx = new Vector();
        
        if (status == 1 || status == 0) {
            if (iCommand == Command.ADD || (iCommand == Command.SAVE && frmKoefisionPosition.errorSize() > 0) || (listKoefisionPosition.size() < 1)) { 
                 rowx.add(""+(limit+nmber+1));
                rowx.add(""+ControlCombo.draw(FrmKoefisionPosition.fieldNames[FrmKoefisionPosition.FRM_FLD_POSITION_ID], "formElemen", null, ""+koefisionPositionn.getPositionId(), vOid, vValue)); 
                rowx.add("<input type=\"text\" name=\"" + FrmKoefisionPosition.fieldNames[FrmKoefisionPosition.FRM_FLD_NILAI_KOEFISION_OUTLET] + "\" value=\"" + koefisionPositionn.getNilaiKoefisionOutlet() + "\" size=\"12\" class=\"elemenForm\">");
                rowx.add("<input type=\"text\" name=\"" + FrmKoefisionPosition.fieldNames[FrmKoefisionPosition.FRM_FLD_NILAI_KOEFISION_DC] + "\" value=\"" + koefisionPositionn.getNilaiKoefisionDc() + "\" size=\"12\" class=\"elemenForm\">");
                
                
                
            }
        }
        lstData.add(rowx);

        return ctrlist.draw();
    }

%>


<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Setup - Salary Component</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        <SCRIPT language=JavaScript>
            
            function cmdEdit(oidKoefisionPosition){
                document.frm_koefision_position.oidKoefisionPosition.value=oidKoefisionPosition;
                document.frm_koefision_position.status.value="0";
                document.frm_koefision_position.command.value="<%=Command.EDIT%>";
                document.frm_koefision_position.prev_command.value="<%=prevCommand%>";
                document.frm_koefision_position.action="koefisionposition.jsp";
                document.frm_koefision_position.submit();
            }
            
            function cmdAdd(){
                
                document.frm_koefision_position.oidKoefisionPosition.value="0";
                document.frm_koefision_position.status.value="1";
                document.frm_koefision_position.command.value="<%=Command.ADD%>";
                document.frm_koefision_position.prev_command.value="<%=prevCommand%>";
                document.frm_koefision_position.action="koefisionposition.jsp";
                document.frm_koefision_position.submit();
            }
            
            function cmdAddD(){
                document.frm_koefision_position.oidKoefisionPosition.value="0";
                document.frm_koefision_position.status.value="2";
                document.frm_koefision_position.command.value="<%=Command.ADD%>";
                document.frm_koefision_position.prev_command.value="<%=prevCommand%>";
                document.frm_koefision_position.action="koefisionposition.jsp";
                document.frm_koefision_position.submit();
            }
            
            function cmdSave(){
                document.frm_koefision_position.command.value="<%=Command.SAVE%>";
                document.frm_koefision_position.status.value="3";
                document.frm_koefision_position.prev_command.value="<%=prevCommand%>";
                document.frm_koefision_position.action="koefisionposition.jsp";
                document.frm_koefision_position.submit();
            }
            
            function cmdBack(){
                document.frm_koefision_position.command.value="<%=Command.BACK%>";
                document.frm_koefision_position.action="koefisionposition.jsp";
                document.frm_koefision_position.submit();
            }
            
            function cmdListFirst(){
                document.frm_koefision_position.command.value="<%=Command.FIRST%>";
                document.frm_koefision_position.prev_command.value="<%=Command.FIRST%>";
                document.frm_koefision_position.action="koefisionposition.jsp";
                document.frm_koefision_position.submit();
            }
            
            function cmdListPrev(){
                document.frm_koefision_position.command.value="<%=Command.PREV%>";
                document.frm_koefision_position.prev_command.value="<%=Command.PREV%>";
                document.frm_koefision_position.action="koefisionposition.jsp";
                document.frm_koefision_position.submit();
            }
            function cmdListNext(){
                document.frm_koefision_position.command.value="<%=Command.NEXT%>";
                document.frm_koefision_position.prev_command.value="<%=Command.NEXT%>";
                document.frm_koefision_position.action="koefisionposition.jsp";
                document.frm_koefision_position.submit();
            }
            
            function cmdListLast(){
                document.frm_koefision_position.command.value="<%=Command.LAST%>";
                document.frm_koefision_position.prev_command.value="<%=Command.LAST%>";
                document.frm_koefision_position.action="koefisionposition.jsp";
                document.frm_koefision_position.submit();
            }
            
            function cmdConfirmDelete(oidKoefisionPosition){
                var x = confirm(" Are You Sure to Delete?");
                if(x){
                    document.frm_koefision_position.command.value="<%=Command.DELETE%>";
                    document.frm_koefision_position.action="koefisionposition.jsp";
                    document.frm_koefision_position.submit();
                }
            }
            
                
            
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
        </SCRIPT>
        <!-- #EndEditable --> 
    </head>
     <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">     
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>  
           <%@include file="../styletemplate/template_header.jsp" %>
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Koefision Position Setting <!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr> 
                                        <td height="20"> </td>
                                    </tr>
                                    <tr> 
                                        <td> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr> 
                                                                <td valign="top"> 
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr> 
                                                                            <td valign="top"> <!-- #BeginEditable "content" --> 
                                                                                <form name="frm_koefision_position" method="post" action="">
                                                                                    <input type="hidden" name="command" value="">
                                                                                   <input type="hidden" name="startKoefisionPosition" value="<%=startKoefisionPosition%>">
                                                                                    <input type="hidden" name="oidKoefisionPosition" value="<%=oidKoefisionPosition%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="status" value="<%=status%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                    <tr> <td>
                                                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">   
                                                                                                <tr align="left" valign="top"> 
                                                                                                    <td  align="left" height="14" valign="middle" colspan="2" class="listtitle">&nbsp;Koefision Position Setting </td>
                                                                                                </tr> 
                                                                                            </table>
                                                                                    </td></tr>
                                                                                    <%
            try {
                // out.println("listBenefit"+listBenefit.size());
                if ((listKoefisionPosition == null || listKoefisionPosition.size() < 1) && (iCommand == Command.NONE)) {
                    iCommand = Command.ADD;
                }

                                                                                    %>
                                                                                    <tr> 
                                                                                    <td width="24%" colspan="2">
                                                                                    <%= drawList(iCommand, frmKoefisionPosition, listKoefisionPosition, status,oidKoefisionPosition,koefisionPosition,startKoefisionPosition)%>
                                                                                    <table cellpadding="0" cellspacing="0" border="0">
                                                                                        <%
            } catch (Exception exc) {
            }
                                                                                        %>
                                                                                        
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td width="519">
                                                                                                
                                                                                                <table width="136%" height="68" border="0">
                                                                                                    
                                                                                                    <tr>
                                                                                                    
                                                                                                      <%
            int cmd = 0;
            if ((iCommand == Command.FIRST || iCommand == Command.PREV) ||
                    (iCommand == Command.NEXT || iCommand == Command.LAST)) {
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
                                                                                        <%=ctrLine.drawImageListLimit(cmd, vectKoefisionPosition, startKoefisionPosition, recordToGet)%> 
                                                                                                                                                       <%
            //	out.println("masukq"+Command.ADD) ;
            if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmKoefisionPosition.errorSize() < 1)) {%>
                                                                                                    <tr> 
                                                                                                        <td width="150"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a>
                                                                                                            <a href="javascript:cmdAdd()" class="command">Add 
                                                                                                        New Koefision Position</a> </td>
                                                                                                    </tr>
                                                                                                    <%}%>
                                                                                                    
            
                                                                                                    <% if (status == 1 || status == 0) {
                if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmKoefisionPosition.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td >
                                                                                                            <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
                                                                                                         
                                                                                                            <a href="javascript:cmdSave()" class="command">Save Koefision</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                                            <img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
                                                                                                            <a href="javascript:cmdConfirmDelete()" class="command">Delete Koefision</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                                            <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                                                                                                        <a href="javascript:cmdBack()" class="command">Back to Koefision</a> </td>
                                                                                                    </tr>
                                                                                                    <%}
            }%>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                       
           
                                                                                    </table>
                                                                                    <table width="100%" border="0">
                                                                                        <tr>
                                                    
         
                                                                                        
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
    <!-- #BeginEditable "script" --> <script language="JavaScript">
        var oBody = document.body;
        var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
