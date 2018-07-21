<%-- 
    Document   : outlet
    Created on : Feb 25, 2014, 3:05:26 PM
    Author     : Satrya Ramayu
--%>
<%@page import="java.rmi.Remote"%>
<%@page import="com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet"%>
<%@page import="com.dimata.gui.jsp.ControlDatePopup"%>
<%@page import="com.dimata.harisma.form.attendance.employeeoutlet.FrmEmployeeOutlet"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.util.LogicParser"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.startdata.PstOutletStart"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_OUTLET, AppObjInfo.OBJ_EMPLOYEE_OUTLET); %> 
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
    //boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;

//cek tipe browser, browser detection
    //String userAgent = request.getHeader("User-Agent");
    //boolean isMSIE = (userAgent!=null && userAgent.indexOf("MSIE") !=-1); 

%>
<%!
    public static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }
    
    
    public static String drawlistLocation(JspWriter outJsp,Vector listSubRegency,String locationSearch){
        String result = "";

        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        //ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        //ctrlist.setStyleSelectableTableValue("id=\"selectable\""); 
        ctrlist.setMaxFreezingTable(1);
        //mengambil nama dari kode komponent
        ctrlist.addHeader("No", "5%", "2", "0");
        ctrlist.addHeader("Sub Regency", "5%", "2", "0");
        //ctrlist.addHeader("Nama", "50%","0","5");
        ctrlist.addHeader("Location Nama", "60%","0", "5");
        ctrlist.addHeader("1", "10%","0", "0");
        ctrlist.addHeader("2", "10%","0", "0");
        ctrlist.addHeader("3", "10%","0", "0");
        ctrlist.addHeader("4", "10%","0", "0");
        ctrlist.addHeader("5", "10%","0", "0");
        //ctrlist.addHeader("6", "10%","0", "0");
        
        
        //ctrlist.setLinkRow(0);
        //ctrlist.setLinkSufix(""); 
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        //ctrlist.setLinkPrefix("javascript:cmdEdit('");
        //ctrlist.setLinkSufix("')");
        ctrlist.reset();

        int no = 0;
        int start=0;
        int recortToGet=5; 
        //update by devin 2014-04-35
        Hashtable getChecked = new Hashtable();
        if(locationSearch!=null && locationSearch.length()>0){ 
            String where=PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]+" IN("+locationSearch+")";
            String order =PstLocation.fieldNames[PstLocation.FLD_SUB_REGENCY_ID]; 
            getChecked=PstLocation.findLocationChecked(0, 0, where, order);   
            
        }
        if (listSubRegency != null && listSubRegency.size() > 0) { 
            ctrlist.drawListHeaderWithJsVer2SelectTable(outJsp);//header 
            //ctrlist.drawLfromLimitistHeaderWithJsVer2(outJsp);
            try {
                boolean chkLokasiNew=true;
                long oidRegency=0;
                
                for (int x = 0; x < listSubRegency.size(); x++) {
                    //Vector rowx = new Vector(1,1);
                    ControlList ctControlList = new ControlList();
                      //Vector rowX=new Vector();
                    Location location = new Location();
                    Kecamatan kecamatan = new Kecamatan();
                   String namacc="";
                   if(chkLokasiNew){
                       kecamatan = (Kecamatan)listSubRegency.get(x);
                       oidRegency= kecamatan.getOID(); 
                     
                        namacc= kecamatan.getNmKecamatan(); 
                   }
                      
                    
                    Vector listLocation = PstLocation.list(start, recortToGet,PstLocation.fieldNames[PstLocation.FLD_SUB_REGENCY_ID]+"="+oidRegency, ""); 
                    //if(listLocation!=null && listLocation.size()>0 && namacc.length()>0){ 
                    if(listLocation!=null && listLocation.size()>0){ 
                        start=start+5;
                        //toLimit=toLimit+5;
                        //recortToGet=5;
                        x=x-1;
                        
                    }else{
                        start=0;
                        //recortToGet=5; 
                        chkLokasiNew=true;
                    }
                        
                         
                    //if(listLocation!=null && listLocation.size()>0 && namacc.length()>0){
                      if(listLocation!=null && listLocation.size()>0){  
                        
                         
                        int locx=0;
                        boolean chkNoCreateNameRegency=true; 
                        for(int i=0;i<listLocation.size();i++){
                           if(chkNoCreateNameRegency){
                            if(chkLokasiNew){ 
                                no = no + 1;
                                ctControlList.addColoms(""+no, "0", "0");
                                ctControlList.addColoms(""+kecamatan.getNmKecamatan(), "0", "0");

                            }else{
                                //rowX.add("");
                                //rowX.add("");
                                ctControlList.addColoms("", "0", "0");
                                ctControlList.addColoms("", "0", "0");
                            }
                          }
                             location = (Location)listLocation.get(i); 
                             locx = locx+1;
                            if(location.getSubRegencyId()==oidRegency){
                                if(locx<6){
                                    //rowX.add(location.getName()); 
                                    String cheked="";
                                    if(getChecked.containsKey(""+location.getOID())){
                                         cheked=(String)getChecked.get(""+location.getOID()); 
                                    }
                                    ctControlList.addColoms("<input type=\"checkbox\" "+cheked+" name=\"locationSearchId\" value=\""+location.getOID()+"\" >"+location.getName(), "0", "0");  
                                    //listLocation.remove(i);
                                   //i=i-1;
                                    chkLokasiNew=true;
                                    chkNoCreateNameRegency=false;
                                    int data=listLocation.size();
                                    
                                    if((5-data)!=0 && (data-i)==1){  
                                        int hasil=5-data;
                                         for(int vx=0;vx<hasil;vx++){  
                                         ctControlList.addColoms("", "0", "0"); 
                                    }
                                    }
                                   
                                    if((locx+1)==6){
                                        chkLokasiNew=false;
                                        chkNoCreateNameRegency=true;
                                        locx=0; 
                                    }
                                }
                                }
                             }
                       
                         ctrlist.drawListRowJsVer2CoolspanRowsPan (outJsp, 0, ctControlList, x);
                        }
                    

                }

            } catch (Exception exc) {
                System.out.println(exc);
            }

            result = "";
            ctrlist.drawListEndTableJsVer2(outJsp);
        } else {
            result = "<i>Belum ada data dalam sistem ...</i>";
        }
        return result;
    }

%>
<%
    long oidDepartment = FRMQueryString.requestLong(request, "department");
    long oidCompany = FRMQueryString.requestLong(request, "hidden_companyId");
     long oidDivision = FRMQueryString.requestLong(request, "hidden_divisionId");
    long oidSection = FRMQueryString.requestLong(request, "section");
    Date selectedDateFrom = FRMQueryString.requestDateVer3(request, "check_date_start");
    Date selectedDateTo = FRMQueryString.requestDateVer3(request, "check_date_finish");
    int iCommand = FRMQueryString.requestCommand(request);
    //long locationSearchId = FRMQueryString.requestLong(request, "locationSearchId");
    long positionId = FRMQueryString.requestInt(request, "positionId");        
    long locationId = FRMQueryString.requestInt(request, "locationId");
    String empNumber = FRMQueryString.requestString(request, "emp_number");
    String fullName = FRMQueryString.requestString(request, "full_name");
    int chekedInculedeSearchDate = FRMQueryString.requestInt(request, "chekedDate"); 
     String tmpLocationSrc[] = request.getParameterValues("locationSearchId");
    String locationSearchIdx=""; 
    if(tmpLocationSrc!=null && tmpLocationSrc.length>0){  
        for(int xs=0;xs<tmpLocationSrc.length;xs++){
            locationSearchIdx = locationSearchIdx + tmpLocationSrc[xs]+",";
        }
        if(locationSearchIdx!=null && locationSearchIdx.length()>0){
            locationSearchIdx = locationSearchIdx.substring(0,locationSearchIdx.length()-1);  
    
        }
    }
    
   /// Vector listLocation = PstLocation.listAll();
    Vector listSubRegency = PstKecamatan.listAll(); 
%>  
<html>
    <head>
         <%@ include file = "../../main/konfigurasi_jquery.jsp" %>    
        <title>Harisma - Employee Outlet</title>
      
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
             <!-- #EndEditable --> 
               <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
             <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <script type="text/javascript" src="../../javascripts/jquery.min.js"></script>
    <script type="text/javascript" src="../../javascripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../../javascripts/gridviewScroll.min.js"></script>
    
    <link href="../../stylesheets/GridviewScroll.css" rel="stylesheet" />
     <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
     <script type="text/javascript">

function changeHashOnLoad() {
window.location.href += "#";
setTimeout("changeHashAgain()", "50");
}

function changeHashAgain() {
window.location.href +="1";
}

var storedHash = window.location.hash;
window.setInterval(function () {
if (window.location.hash != storedHash) {
window.location.hash = storedHash;
}
}, 50);

</script>
        <SCRIPT language=JavaScript>
function cmdUpdateDiv(){
    document.frmoutlet.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frmoutlet.action="outlet.jsp";
    document.frmoutlet.submit();
}
function cmdUpdateDep(){
    document.frmoutlet.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frmoutlet.action="outlet.jsp";
    document.frmoutlet.submit();
}
function cmdUpdatePos(){
    document.frmoutlet.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frmoutlet.action="outlet.jsp";
    document.frmoutlet.submit();
}
function cmdView(){
                document.frmoutlet.command.value="<%=Command.LIST%>";
                document.frmoutlet.sourceOutlerSearch.value="outletedit";
                document.frmoutlet.action="outlet_edit.jsp";
                document.frmoutlet.submit();
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

        </SCRIPT>
           <!-- #EndEditable --> 
    </head> 
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
                   
                    <%@ include file = "../../main/header.jsp" %>
                    
                </td>
            </tr>
            <tr>
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" -->
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
            
            <tr>
                <td width="88%" valign="top" align="left">
                   
                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr>
                            <td width="100%">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td height="20">
                                            <font color="#FF6600" face="Arial"><strong>
                                                Outlet &gt; Employee Outlet
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
                                                                                <form name="frmoutlet" action="" method="post">
                                                                                    <input type="hidden" name="command" value=""/>
                                                                                    <input type="hidden" name="sourceOutlerSearch" value=""/>
                                                                                   
                                                                                    
                                                                                <table  width="100%">
                                                                                    <tr>
                                                                            <td valign="top">
                                                                              
                                                                                  
                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                    <tr>
                                                                                        <td width="6%" nowrap="nowrap"><div align="left">Payrol Num </div></td>
                                                                                        <td width="30%" nowrap="nowrap">:
                                                                                            <input class="masterTooltip" type="text" size="40" name="emp_number"  value="<%=empNumber%>" title="You can Input Payroll Number more than one, ex-sample : 1111,2222" class="elemenForm" onKeyDown="javascript:fnTrapKD()"></input>                                                                                        </td>
                                                                                        <td width="5%" nowrap="nowrap"> Full Name </td>
                                                                                        <td width="59%" nowrap="nowrap">:
                                                                                            <input class="masterTooltip" type="text" size="50" name="full_name"  value="<%=fullName%>" title="You can Input Full Name more than one, ex-sample : saya,kamu" class="elemenForm" onKeyDown="javascript:fnTrapKD()">                                                                                        </td>
                                                                                      </tr>
                                                                                     
          <tr>
              <td width="6%" nowrap="nowrap"><div align="left">Company </div></td>
              <td width="30%" nowrap="nowrap">:
                <%
					Vector comp_value = new Vector(1, 1);
					Vector comp_key = new Vector(1, 1);  
String whereComp="";     
    Vector div_value = new Vector(1, 1);
    Vector div_key = new Vector(1, 1);      
    
    Vector dept_value = new Vector(1, 1);
    Vector dept_key = new Vector(1, 1);                                      
 if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                   comp_value.add("0");
                   comp_key.add("select ...");
                   
                    div_value.add("0");
                   div_key.add("select ...");
                   
                    dept_value.add("0");
                   dept_key.add("select ...");
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                    String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                    // comp_value.add("0");
                    // comp_key.add("select ...");
                     
                       //div_value.add("0");
                       //div_key.add("select ...");
                   
                       dept_value.add("0");
                       dept_key.add("select ...");
                    
                     whereComp = whereComp!=null && whereComp.length()>0 ? whereComp + " AND ("+  whereDiv +")": whereDiv;
                    
                } else {

                    String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                            + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
                    try {
                        String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                        Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                        int grpIdx = -1;
                        int maxGrp = depGroup == null ? 0 : depGroup.size();
                        int countIdx = 0;
                        int MAX_LOOP = 10;
                        int curr_loop = 0;
                        do { // find group department belonging to curretn user base in departmentOid
                            curr_loop++;
                            String[] grp = (String[]) depGroup.get(countIdx);
                            for (int g = 0; g < grp.length; g++) {
                                String comp = grp[g];
                                if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                    grpIdx = countIdx;   // A ha .. found here 
                                }
                            }
                            countIdx++;
                        } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                        // compose where clause
                        if (grpIdx >= 0) {
                            String[] grp = (String[]) depGroup.get(grpIdx);
                            for (int g = 0; g < grp.length; g++) {
                                String comp = grp[g];
                                whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                            }
                        }
                    } catch (Exception exc) {
                            System.out.println(" Parsing Join Dept" + exc);

                    }
                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                    
                     whereComp = whereComp!=null && whereComp.length()>0 ? whereComp + " AND ("+ whereClsDep +")":whereClsDep;
                     
                }
            }
        }
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
     div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }               
    Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    String prevCompany="";
    String prevDivision="";
    


long prevCompanyTmp=0;        
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                //if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                //}
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
            String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
             //untuk karyawan admin yg hanya bisa akses departement tertentu (ketika di awal)
             ////update
if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                if(oidCompany!=0){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 

                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                    prevCompany = dept.getCompany();
                    prevDivision = dept.getDivision();             
                }
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) { 
                    if(oidCompany!=0){
                       div_key.add(dept.getDivision());
                       div_value.add(""+dept.getDivisionId()); 

                       dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision();             
                   }
                    //update by satrya 2013-09-19
                    else if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){
                        div_key.add(dept.getDivision());
                        div_value.add(""+dept.getDivisionId()); 
                        
                        //update by satrya 2013-09-19
                        dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision(); 
                   }
                   
                }else{
                    
                     div_key.add(dept.getDivision());
                       div_value.add(""+dept.getDivisionId()); 

                       dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       //update by satrya 2014-09-18
                       if(dept_value!=null && dept_value.size()==1){
                           oidDepartment = dept.getOID();
                       }
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision(); 
                }
            }
        }
 }else{
      if(oidCompany!=0){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 

                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                    prevCompany = dept.getCompany();
                    prevDivision = dept.getDivision();             
       }
 }
            
            }
           else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }
           
        }
    }  
			%>
                <%= ControlCombo.draw("hidden_companyId", "formElemen", null, ""+oidCompany, comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%> </td>
              <td width="5%" nowrap="nowrap"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
              <td width="59%" nowrap="nowrap">:
                <%
                                   if(oidCompany==0){ 
                                      oidDivision=0; 
                                   }

if(oidCompany!=0){
    whereComp = "("+(whereComp!=null && whereComp.length()==0?"1=1":whereComp) + ") AND " + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] +"="+oidCompany;
 listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    prevCompany="";
    prevDivision="";
    
    div_value = new Vector(1, 1);
    div_key = new Vector(1, 1);      
    
    dept_value = new Vector(1, 1);
    dept_key = new Vector(1, 1); 

    prevCompanyTmp=0;  
long tmpFirstDiv=0;   

if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                   comp_value.add("0");
                   comp_key.add("select ...");
                   
                   div_value.add("0");
                   div_key.add("select ...");
                   
                   dept_value.add("0");
                   dept_key.add("select ...");
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) { 
                       dept_value.add("0");
                       dept_key.add("select ...");
                    
                } 
            }
        }
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
    div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }
   long prevDivTmp=0;
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                //update
                if(oidDivision!=0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                }
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
           String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             if(prevDivTmp!=dept.getDivisionId()){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 
                    prevDivTmp=dept.getDivisionId();
              }
             
                    tmpFirstDiv=dept.getDivisionId(); 
            prevCompany = dept.getCompany();
            prevDivision = dept.getDivision();             
            }
          String chkAdaDpt="";
          if(whereComp!=null && whereComp.length()>0){
                chkAdaDpt = "("+(whereComp!=null && whereComp.length()==0?"1=1":whereComp)+ ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+oidDivision;
          }
            Vector listCheckAdaDept = PstDepartment.listWithCompanyDiv(0, 0, chkAdaDpt);
            if((listCheckAdaDept==null || listCheckAdaDept.size()==0)){
                
if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId()); 
                } catch (Exception exc) {
                }
                
                oidDivision=tmpFirstDiv;
              
            }
        }
 }else{
    oidDivision = tmpFirstDiv;
     
 }
               
            }
        }
    }
}
			%>
                <%= ControlCombo.draw("hidden_divisionId", "formElemen", null,""+oidDivision , div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%> </td>
            </tr>
            <tr>
              <td width="6%" align="right" nowrap><div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
              <td width="30%" nowrap="nowrap"> :
                <%

            //update by satrya 2013-08-13
            //jika user memilih select kembali
            if(oidDepartment==0){  
                oidSection=0;
            }
if(oidDivision!=0){
    if(whereComp!=null && whereComp.length()>0){
        whereComp = "("+whereComp + ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+oidDivision;
    }
    
    listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    prevCompany="";
    prevDivision="";
    
    div_value = new Vector(1, 1);
    div_key = new Vector(1, 1);      
    
    dept_value = new Vector(1, 1);
    dept_key = new Vector(1, 1); 

    prevCompanyTmp=0; 

if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                   comp_value.add("0");
                   comp_key.add("select ...");
                   
                   div_value.add("0");
                   div_key.add("select ...");
                   
                   dept_value.add("0");
                   dept_key.add("select ...");
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) { 
                       //div_value.add("0");
                       //div_key.add("select ...");
                   
                       dept_value.add("0");
                       dept_key.add("select ...");
                    
                } 
            }
        }
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
    div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }
                
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
            String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             comp_key.add(dept.getCompany());
             comp_value.add(""+dept.getCompanyId());
             
             
             div_key.add(dept.getDivision());
             div_value.add(""+dept.getDivisionId()); 
              
             dept_key.add(dept.getDepartment());
             dept_value.add(String.valueOf(dept.getOID()));
            prevCompany = dept.getCompany();
            prevDivision = dept.getDivision();             
            }else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }
           
        }
    }
}
			%>
                <%= ControlCombo.draw("department", "formElemen", null, "" + oidDepartment, dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%> </td>
              <td width="5%" align="left" nowrap valign="top"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
              <td width="59%" nowrap="nowrap">:
                <%

					Vector sec_value = new Vector(1, 1);
					Vector sec_key = new Vector(1, 1);
					sec_value.add("0");
					sec_key.add("select ...");

					//String sWhereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + sSelectedDepartment;                                                       
					//Vector listSec = PstSection.list(0, 0, sWhereClause, " SECTION ");
					String secWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
					Vector listSec = PstSection.list(0, 0, secWhere, " SECTION ");
					for (int i = 0; i < listSec.size(); i++) {
						Section sec = (Section) listSec.get(i);
						sec_key.add(sec.getSection());
						sec_value.add(String.valueOf(sec.getOID()));
					}
				%>
                <%=ControlCombo.draw("section", null, "" + oidSection, sec_value, sec_key)%></td>
            </tr>
            <!-- update by devin 2014-04-29  -->
            <tr>
                 <td width="6%" align="right" nowrap><div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div></td>
              <td width="30%" nowrap="nowrap"> :
                <%
				Vector position_value = new Vector(1, 1);
				Vector position_key = new Vector(1, 1);
                               
				Vector  listPosition = PstPosition.list(0, 0, "", PstPosition.fieldNames[PstPosition.FLD_POSITION]);
				for (int r = 0; r < listPosition.size(); r++) {
					Position position = (Position) listPosition.get(r);
					 position_key.add(position.getPosition());
					position_value.add(String.valueOf(position.getOID())); 
				}  
				%>
                <%=ControlCombo.draw("positionId", "select...", ""+positionId, position_value,  position_key, "onkeydown=\"javascript:fnTrapKD()\"")%> </td>
            </tr>
            <tr>
              <td width="6%" align="right" nowrap><div align=left>Date</div></td>
              <td width="30%" nowrap="nowrap">:
                <% 
				Date st = new Date();
				st.setHours(0);
				st.setMinutes(0);
				st.setSeconds(0);
				Date end = new Date();
				end.setHours(23);
				end.setMinutes(59);
				end.setSeconds(59);
				 String ctrTimeStart = ControlDate.drawTime("check_date_start",  selectedDateFrom != null ? selectedDateFrom : st, "elemenForm", 24,0, 0); 
				 String ctrTimeEnd = ControlDate.drawTime("check_date_finish",  selectedDateTo != null ?  iCommand==Command.NONE ? end : selectedDateTo : end, "elemenForm", 24,0, 0); 
                                 String cheked = chekedInculedeSearchDate == 1? "checked":"";
				 %>
                
                                 <input type="checkbox" name="chekedDate" value="1" <%=cheked%>/>filter Date 
                <%=ControlDate.drawDateWithStyle("check_date_start", selectedDateFrom != null ? selectedDateFrom : null, 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeStart%>
                to <%=ControlDate.drawDateWithStyle("check_date_finish", selectedDateTo != null ? selectedDateTo : null, 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeEnd%>              </td>  
              
                               
            </tr>
                <tr>
                    <td>Choose view</td>
                    <td>: <select name="view_mode"><option value="1">With time</option><option value="2" selected="selected">Without time</option></select></td>
                    <td>&nbsp;</td>
                </tr>
            <br />
           <!-- <tr>
              <td width="6%" align="right" nowrap><div align=left>Location</div></td>
              <td width="30%" nowrap="nowrap">:
              <%
                   /* Vector loc_value = new Vector(1, 1);
                    Vector loc_key = new Vector(1, 1);
                     Vector colorLocation = new Vector(1,1);
                    Vector  listLocation = PstLocation.list(0, 0, "", PstLocation.fieldNames[PstLocation.FLD_NAME]);
                    for (int r = 0; r < listLocation.size(); r++) {
                            Location location = (Location) listLocation.get(r);
                             loc_key.add(location.getName());
                            loc_value.add(String.valueOf(location.getOID()));
                            colorLocation.add("#"+location.getColorLocation());                            
                    }  
                     String color="#FFFFF";
                        if(locationSearchId!=0){ 
                            Location loc= new Location(); 
                            try{
                                 loc = PstLocation.fetchExc(locationSearchId);
                            }catch(Exception exc){

                            }   
                            color="#"+loc.getColorLocation();
                        }*/
                    %>
                    <%//=ControlCombo.drawComboBoxColor("locationSearchId", "select...", ""+locationSearchId, loc_value,  loc_key, "onkeydown=\"javascript:fnTrapKD()\"",color,colorLocation)%>
              </td>  
              
              <td width="5%" align="right" nowrap></td>
              <td width="59%" nowrap="nowrap"></td>
            </tr>-->
              
            
            <tr>
              <td width="6%" align="right" nowrap><div align=left>Location</div></td>
              <td colspan="3" nowrap="nowrap">:
              <%
                  /*Vector loc_value = new Vector(1, 1);
                    Vector loc_key = new Vector(1, 1);
                     Vector colorLocation = new Vector(1,1);
                    Vector  listLocation = PstLocation.list(0, 0, "", PstLocation.fieldNames[PstLocation.FLD_NAME]);
                    for (int r = 0; r < listLocation.size(); r++) {
                            Location location = (Location) listLocation.get(r);
                             loc_key.add(location.getName());
                            loc_value.add(String.valueOf(location.getOID()));
                            colorLocation.add("#"+location.getColorLocation());                            
                    }  
                     String color="#FFFFF";
                        if(locationSearchId!=0){ 
                            Location loc= new Location(); 
                            try{
                                 loc = PstLocation.fetchExc(locationSearchId);
                            }catch(Exception exc){

                            }   
                            color="#"+loc.getColorLocation();
                        }*/
                    %>
                    <%//=ControlCombo.drawComboBoxColor("locationSearchId", "select...", ""+locationSearchId, loc_value,  loc_key, "onkeydown=\"javascript:fnTrapKD()\"",color,colorLocation)%>              
                    <%=drawlistLocation(out   ,listSubRegency,locationSearchIdx)%>
              </td>  
              </tr>
                                                                                </table>
           
                                                                            </td>
                                                                        </tr>
<tr>
    <td>
        <table border="0" cellspacing="0" cellpadding="0" width="137">
                  <tr>
                    <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                    <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                    <td width="94" class="command" nowrap><a href="javascript:cmdView()">View Employee</a></td>
                  </tr>
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
                         
    </td>
</tr>
<%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               
                                <%//@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20">
      <%//@ include file = "../../main/footer.jsp" %>
               </td>
            </tr>
            <%}%>
</table>
   <script type="text/javascript">
	    $(document).ready(function () {
	        gridviewScroll();
	    });
            <%
                int freesize=2;
                
            %>
	    function gridviewScroll() {
	        gridView1 = $('#GridView1').gridviewScroll({
                width: 1100,
                height: 300,
                railcolor: "##33AAFF",
                barcolor: "#CDCDCD",
                barhovercolor: "#606060",
                bgcolor: "##33AAFF",
                freezesize: <%=freesize%>,
                arrowsize: 30,
                varrowtopimg: "<%=approot%>/images/arrowvt.png",
                varrowbottomimg: "<%=approot%>/images/arrowvb.png",
                harrowleftimg: "<%=approot%>/images/arrowhl.png",
                harrowrightimg: "<%=approot%>/images/arrowhr.png",
                headerrowcount: 2,
                railsize: 16,
                barsize: 15
            });
	    }
	</script>
</body>
                  <!-- #BeginEditable "script" --> 
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>


                